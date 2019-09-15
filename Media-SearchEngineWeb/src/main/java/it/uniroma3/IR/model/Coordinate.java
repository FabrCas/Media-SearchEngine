package it.uniroma3.IR.model;

public class Coordinate {
	private Long x;
	private Long y;
	private Long width;
	private Long height;
	
	public Coordinate(Long x, Long y, Long width, Long height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	

	@Override
	public boolean equals(Object obj) {
		Coordinate other = (Coordinate) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}

	public Long getX() {
		return x;
	}
	public void setX(Long x) {
		this.x = x;
	}
	public Long getY() {
		return y;
	}
	public void setY(Long y) {
		this.y = y;
	}
	public Long getWidth() {
		return width;
	}
	public void setWidth(Long width) {
		this.width = width;
	}
	public Long getHeight() {
		return height;
	}
	public void setHeight(Long height) {
		this.height = height;
	}

}
