package kz.xardbaiz.potracej.utils;

import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGPath;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

public class SVGUtils {

	public static SVGDocument createSvgDocument(int width, int height) {
		DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();
		SVGDocument document = (SVGDocument) domImpl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg",
				null);
		Element svgTag = document.getRootElement();
		svgTag.setAttribute("width", String.valueOf(width));
		svgTag.setAttribute("height", String.valueOf(height));
		return document;
	}

	public static void putPathToSvgDocument(SVGDocument document, GeneralPath path) {
		SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(document);

		String pathData = SVGPath.toSVGPathData(path, ctx);

		SVGPath svgPath = new SVGPath(ctx);
		Element svgElement = svgPath.toSVG(path);
		svgElement.setAttribute("fill", "#000");
		document.getRootElement().appendChild(svgElement);
	}

	public static void saveSvgDocumentToFile(SVGDocument document, File file)
			throws FileNotFoundException, IOException {
		SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
		try (Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
			svgGenerator.stream(document.getDocumentElement(), out);
		}
	}
}
