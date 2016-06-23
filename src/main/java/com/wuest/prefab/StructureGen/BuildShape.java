package com.wuest.prefab.StructureGen;

import net.minecraft.util.EnumFacing;

/**
 * This class holds the general shape of the structure.
 * @author WuestMan
 */
public class BuildShape
{
	private int width;
	private int height;
	private int length;
	private EnumFacing direction;
	
	public BuildShape()
	{
		this.Initialize();
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public void setWidth(int value)
	{
		this.width = value;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public void setHeight(int value)
	{
		this.height = value;
	}
	
	public int getLength()
	{
		return this.length;
	}
	
	public void setLength(int value)
	{
		this.length = value;
	}
	
	public EnumFacing getDirection()
	{
		return this.direction;
	}
	
	public void setDirection(EnumFacing value)
	{
		this.direction = value;
	}
	
	public void Initialize()
	{
		this.width = 0;
		this.height = 0;
		this.length = 0;
		this.direction = EnumFacing.NORTH;
	}
}
