package kz.xardbaiz.potracej;

import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGDocument;

import kz.xardbaiz.potracej.utils.SVGUtils;
import uk.sannysanoff.potracej.compat.ConvertToJavaCurves;
import uk.sannysanoff.potracej.compat.PathElement;
import uk.sannysanoff.potracej.potracej.Bitmap;
import uk.sannysanoff.potracej.potracej.PoTraceJ;
import uk.sannysanoff.potracej.potracej.param_t;
import uk.sannysanoff.potracej.potracej.path_t;

public class BitmapTracer {

	private static int DEFAULT_SCALE = 1;

	// Copy logic from MAIN about tracing - done
	// SAVE path to SVGPath - done
	// TODO : export SVG document to String

	public static SVGDocument toSVG(BufferedImage sourceImage) {
		WritableRaster raster = sourceImage.getRaster();
		int[] iarr = new int[4];
		Bitmap bmp = new Bitmap((int) (sourceImage.getWidth()), (int) (sourceImage.getHeight()));
		for (int y = 0; y < sourceImage.getHeight(); y++) {
			for (int x = 0; x < sourceImage.getWidth(); x++) {
				int[] pixel = raster.getPixel(x, y, iarr);
				if (pixel[0] + pixel[1] + pixel[2] + pixel[3] != 0) {
					bmp.put(x, y, 1);
				}
			}
		}

		// Setting default (sharp) params
		param_t param = new param_t();
		param.opticurve = 0;
		param.opttolerance = 0;
		param.turdsize = 0;

		// TODO: calc alphamax (for rounded corners)
		param.alphamax = 0;

		return doTrace(bmp, sourceImage.getWidth(), sourceImage.getHeight(), param, DEFAULT_SCALE);
	}

	private static SVGDocument doTrace(Bitmap bmp, int width, int height, param_t param, double scale) {
		PoTraceJ poTraceJ = new PoTraceJ(param);
		long l = System.currentTimeMillis();

		path_t trace = null;
		for (int i = 0; i < 10; i++) {
			trace = poTraceJ.trace(bmp);
			Thread.yield();
		}
		poTraceJ.resetTimers();
		for (int i = 0; i < 100; i++) {
			trace = poTraceJ.trace(bmp);
		}
		l = System.currentTimeMillis() - l;
		ArrayList<PathElement> al = new ArrayList<PathElement>();
		ConvertToJavaCurves.convert(trace, new HashSet<ConvertToJavaCurves.Point>(), al);

		GeneralPath path = new GeneralPath();
		for (PathElement pathElement : al) {
			switch (pathElement.getType()) {
			case CLOSE_PATH:
				path.closePath();
				break;
			case LINE_TO:
				path.lineTo(pathElement.getP0x(), pathElement.getP0y());
				break;
			case MOVE_TO:
				path.moveTo(pathElement.getP0x(), pathElement.getP0y());
				break;
			case CURVE_TO:
				path.curveTo(pathElement.getP0x(), pathElement.getP0y(), pathElement.getP1x(), pathElement.getP1y(),
						pathElement.getP2x(), pathElement.getP2y());
				break;
			}
		}

		SVGDocument document = SVGUtils.createSvgDocument(width, height);
		SVGUtils.putPathToSvgDocument(document, path);
		return document;
	}
}
