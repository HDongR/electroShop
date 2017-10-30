package ryu.park.shop.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import ryu.park.shop.utils.BoardPager;
import ryu.park.shop.vo.AddrVO; 
  
@Service
public class ThirdApiServiceImpl implements ThirdApiService {

	@Value("${kpostpostal.url}")
	private String postalApiUrl;

	@Value("${kpostpostal.api}")
	private String postalApiKey;
 
	private static final Logger logger = LoggerFactory.getLogger(ThirdApiServiceImpl.class);
 
	@Override
	public HashMap<String, Object> getPostAndAddress(String name, int pCurrentPage) {

		HashMap<String, Object> result = null;
		BoardPager pager;
		List<AddrVO> addrs;

		HttpURLConnection conn = null;
		try {
			StringBuffer sb = new StringBuffer(3);
			sb.append(postalApiUrl);
			sb.append("?regkey=" + postalApiKey + "&target=postNew&query=");
			sb.append(URLEncoder.encode(name, "EUC-KR"));
			sb.append("&currentPage=" + pCurrentPage);
			sb.append("&countPerPage=" + 10);
			String query = sb.toString();

			URL url = new URL(query);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("accept-language", "ko");
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			byte[] bytes = new byte[4096];
			InputStream in = conn.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (true) {
				int red = in.read(bytes);
				if (red < 0)
					break;
				baos.write(bytes, 0, red);
			}
			String xmlData = baos.toString("utf-8");
		
			baos.close();
			in.close();
			conn.disconnect();

			Document doc = docBuilder.parse(new InputSource(new StringReader(xmlData)));
			Element pageInfoEl = (Element) doc.getElementsByTagName("pageinfo").item(0);
			Element addrEl = (Element) doc.getElementsByTagName("itemlist").item(0);
			if (pageInfoEl == null || addrEl == null) {
				if (conn != null)
					conn.disconnect();
				return null;
			}

			String totalCount = null;
			String currentPage = null;

			for (int i = 0; i < pageInfoEl.getChildNodes().getLength(); i++) {
				Node node = pageInfoEl.getChildNodes().item(i);
				String nodeName = node.getNodeName(); 
				if (nodeName.equals("totalCount")) {
					totalCount = node.getChildNodes().item(0).getNodeValue();
				} else if (nodeName.equals("currentPage")) {
					currentPage = node.getChildNodes().item(0).getNodeValue();
				}
			}

			result = new HashMap<String, Object>();
			pager = new BoardPager(Integer.valueOf(totalCount), Integer.valueOf(currentPage), 10);
			addrs = new ArrayList<AddrVO>();
			int addrNum = 0;
			for (int i = 0; i < addrEl.getChildNodes().getLength(); i++) {
				Node node = addrEl.getChildNodes().item(i);

				if (!node.getNodeName().equals("item")) {
					continue;
				}
				String postcd = node.getChildNodes().item(1).getFirstChild().getNodeValue();
				String address = node.getChildNodes().item(3).getFirstChild().getNodeValue();
				String addrjibun = node.getChildNodes().item(5).getFirstChild().getNodeValue();

				addrs.add(new AddrVO(postcd, address, addrjibun, addrNum));
				
				++addrNum;
			}

			result.put("pager", pager);
			result.put("addrList", addrs);

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.disconnect();
			} catch (Exception e) {
			}
		}

		return result;
	}

}
