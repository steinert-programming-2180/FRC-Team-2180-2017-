package org.usfirst.frc.team2180.robot;

public class ClosedSystem {
	/* Team 2180 (Zero Gravity) 2017 
	 * Max N., Ravi "Fully Automated Luxury Gay Space Communism" D'Leia
	 
	 Class creates a closed mathematical system in which numbers can overflow/underflow in, ideal for angular math.
	 
	 Object Creation:
	 
	 >ClosedSystem 4Corners = new ClosedSystem(int iValue, int iTop, int iBottom);
	 	Creates a closed system of integers with:
	 	the current value iValue
	 	the inclusive maximum iTop
	 	the inclusive minimum iBottom
	 	
	 >ClosedSystem Circle = new ClosedSystem(double dValue, double dTop, boolean topInclusive, double dBottom, boolean bottomInclusive);
	 	Creates a closed system of doubles with:
	 	the current value dValue
	 	the maximum dTop
	 	whether or not the maximum is inclusive topInclusive
	 	the minimum dBottom
	 	whether or not the minimum is inclusive bottomInclusive
	 
	 Methods:
	 Each integer method has it's corresponding double method; everything listed with integer or int can be replaced with double.
	 
	 >4Corners.intGet()
	 	Returns the current value of the closed system iValue
	 >4Corners.intMath(int Value)
	 	Returns the simplification of expression Value inside the closed system
	 	I.E. in a system whose iTop=4 and iBottom=1, 4Corners.intMath(3+2) would return 1
	 >4Corners.intSett(int Value)
	 	Stores the value of the integer inside the closed system to iValue to the closed system
	 	I.E. in a system whose iTop=4 and iBottom=1, 4Corners.intSet(3+2) would store 1 to iValue
	 */
	private int iValue;
	private int iTop;
	private int iBottom;
	
	private double dValue;
	private double dTop;
	private boolean topInclusive;
	private double dBottom;
	private boolean bottomInclusive;
	
	public ClosedSystem(int iValue,int iTop,int iBottom){
		this.iTop=iTop;
		this.iBottom=iBottom;
		
		this.iValue=iformat(iValue);
	}
	
	public ClosedSystem(double dValue,double dTop,boolean topInclusive,double dBottom,boolean bottomInclusive){
		this.dTop=dTop;
		this.dBottom=dBottom;
		this.topInclusive=topInclusive;
		this.bottomInclusive=bottomInclusive;
		
		this.dValue=dformat(dValue);
	}
	
	public int intGet(){
		return iValue;
	}
	
	public int intMath(int value){
		return iformat(value);
	}
	
	public void intSet(int value){
		this.iValue=value;
	}
	
	public double doubleGet(){
		return dValue;
	}
	
	public double doubleMath(double value){
		return dformat(value);
	}
	
	public void doubleSet(double value){
		this.dValue=value;
	}
	
	
	//Formats an arbitrary double into the system
	//ASSIMILATE
	private int iformat(int value){
		boolean inFormat = false;
		while(!inFormat){
			if(value<=iBottom){
				inFormat=false;
				value = value+iTop;
			}else if(value>=iTop){
				inFormat=false;
				value = value-iTop;
			}else{
				inFormat = true;
			}
		}
		return value;
	}
	//Formats an arbitrary double into the system
	//ASSIMILATE
	private double dformat(double value){
		double aValue=value;
		boolean inFormat = false;
		while(!inFormat){
			if(this.topInclusive){
				if(this.bottomInclusive){
					//Top Inclusive, Bottom Inclusive
					if(value<=this.dBottom){
						inFormat=false;
						aValue = aValue+dTop;
					}else if(aValue>=this.dTop){
						inFormat=false;
						aValue = aValue-this.dTop;
					}else
						inFormat  = true;
				}else{
					//Top Inclusive, Bottom Exclusive
					if(aValue<this.dBottom){
						inFormat=false;
						aValue = aValue+this.dTop;
					}else if(aValue>=this.dTop){
						inFormat=false;
						aValue = aValue-this.dTop;
					}else{
						inFormat  = true;
					}
				}
			}else{
				if(this.bottomInclusive){
					//Top Exclusive, Bottom Inclusive
					if(value<=this.dBottom){
						inFormat=false;
						aValue = aValue+this.dTop;
					}else if(aValue>this.dTop){
						inFormat=false;
						aValue = aValue-this.dTop;
					}else
						inFormat  = true;
				}else{
					//Top Exclusive, Bottom Exclusive
					if(aValue<this.dBottom){
						inFormat=false;
						aValue = aValue+this.dTop;
					}else if(aValue>this.dTop){
						inFormat=false;
						aValue = aValue-this.dTop;
					}else{
						inFormat  = true;
					}
				}
			}
		}
		return aValue;
	}
}
