package kz.xardbaiz.potracej.models;

import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.util.StringJoiner;

// http://css.yoksel.ru/svg-fill-and-stroke/
public class VectorFigure {
	// PATH:
	private GeneralPath figurePath;

	// FILL:
	private Color nonStrokingColor;

	// STROKING:
	private Color strokingColor;
	private int strokeWidth = 1;
	private String lineCap = LineCap.BUTT.name().toLowerCase();
	private String lineJoin = LineJoin.MITER.name().toLowerCase();
	private String dashArray;

	public VectorFigure(GeneralPath figurePath, Color strokingColor, Color nonStrokingColor) {
		this(figurePath);
		this.strokingColor=strokingColor;
		this.nonStrokingColor=nonStrokingColor;
	}
	
	public VectorFigure(GeneralPath figurePath) {
		this.figurePath=figurePath;
	}
	
	public Color getStrokingColor() {
		return strokingColor;
	}

	public void setStrokingColor(Color strokingColor) {
		this.strokingColor = strokingColor;
	}

	public Color getNonStrokingColor() {
		return nonStrokingColor;
	}

	public void setNonStrokingColor(Color nonStrokingColor) {
		this.nonStrokingColor = nonStrokingColor;
	}

	public GeneralPath getFigurePath() {
		return figurePath;
	}
	
	public int getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	
	public String getLineCap() {
		return lineCap;
	}

	/** butt = 0; round=1; squere=2 */
	public void setLineCap(int lineCapValue) {
		setLineCap(LineCap.fromValue(lineCapValue).name().toLowerCase());
	}

	/**
	 *  'butt', 'round' or 'square'.
	 *  Default : butt.
	 */
	public void setLineCap(String lineCap) {
		this.lineCap = lineCap;
	}
	
	public String getLineJoin() {
		return lineJoin;
	}

	/** miter = 0; round = 1; bevel = 2; */
	public void setLineJoin(int lineJoinValue) {
		setLineJoin(LineJoin.fromValue(lineJoinValue).name().toLowerCase());
	}
	
	/**
	 *  'miter', 'round' or 'bevel'
	 *  Default : miter.
	 */
	public void setLineJoin(String lineJoin) {
		this.lineJoin = lineJoin;
	}
	

	public String getDashArray() {
		return dashArray;
	}

	public void setDashArray(float[] dashArrayValues) {
		StringJoiner stringJoiner = new StringJoiner(" ");
		for (int i = 0; i < dashArrayValues.length; i++) {
			stringJoiner.add(String.valueOf(dashArrayValues[i]));
		}
		setDashArray(stringJoiner.toString());
	}
	
	public void setDashArray(String dashArray) {
		this.dashArray = dashArray;
	}
	
	
	// ===============================
	// ============ ENUMS ============

	private enum LineCap {
		BUTT(0),
		ROUND(1),
		SQUERE(2);
		
		private int value;
		LineCap(int value) {
			this.value=value;
		}
		
		public static LineCap fromValue(int value) {
			LineCap[] enumValues = LineCap.values();
			for (int i = 0; i < enumValues.length; i++) {
				if (enumValues[i].value == value) {
					return enumValues[i];
				}
			}
			return BUTT;
		}
	}
    
	private enum LineJoin {
		MITER(0), 
		ROUND(1), 
		BEVEL(2);
		
		private int value;
		LineJoin(int value) {
			this.value=value;
		}
		
		public static LineJoin fromValue(int value) {
			LineJoin[] enumValues = LineJoin.values();
			for (int i = 0; i < enumValues.length; i++) {
				if (enumValues[i].value == value) {
					return enumValues[i];
				}
			}
			return MITER;
		}
	}
}
