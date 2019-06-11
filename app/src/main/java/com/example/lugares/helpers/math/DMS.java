package com.example.lugares.helpers.math;

/**
 * Degrees-Minutes-Seconds
 */
public class DMS {
	private double degrees;
	private double minutes;
	private double seconds;

	public DMS(){
		this.degrees = 0;
		this.minutes = 0;
		this.seconds = 0;
	}

	public DMS( double degrees, double minutes, double seconds ){
		this.degrees = degrees;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public DMS( double degrees, boolean all_integers ){
		int iAux = (int)degrees;
		this.degrees = iAux;
		// minutes
		double aux = (degrees - this.degrees) * 60;
		iAux = (int)aux;
		this.minutes = aux;
		// seconds = (degrees - dms.degrees)*3600 - dms.minutes*60
		aux = (aux - this.minutes) * 60;
		if( all_integers ){
			iAux = (int)aux;
			this.seconds = iAux;
		}
		else this.seconds = aux;
	}

	public DMS( double degrees ){
		this( degrees, false );
	}

	public double getDegrees() {
		return degrees;
	}

	public void setDegrees( double degrees ) {
		this.degrees = degrees;
	}

	public double getMinutes() {
		return minutes;
	}

	public void setMinutes( double minutes ) {
		this.minutes = minutes;
	}

	public double getSeconds() {
		return seconds;
	}

	public void setSeconds( double seconds ) {
		this.seconds = seconds;
	}

	public double toDegrees(){
		//return DMS.toDegrees( this.degrees, this.minutes, this.seconds );
		return( degrees + minutes / (double)60 + seconds / (double)3600 );
	}

	public static double toDegrees( double degrees, double minutes, double seconds ){
		return( degrees + minutes / (double)60 + seconds / (double)3600 );
	}

	public static double degreesToRadians( double degrees ){ return Constants.R_2PI_360 * degrees; }
	public static double radiansToDegrees( double radians ){ return Constants.R_360_2PI * radians; }
	// Rotate from an angle to another angle both given in (-Pi, Pi] limited by a max rotation (> 0)
	public static double maxRotationForRotateTo( double angle, double angle_from, double max_radians ){
		double rot = angle - angle_from;
		if( rot < 0.0 ){
			if( rot < -Constants.R_PI ){
				rot = Constants.R_2PI + rot; // > 0
				return rot > max_radians ? max_radians : rot;
			}
			return -rot > max_radians ? -max_radians : rot;
		}
		if( rot > Constants.R_PI ){
			rot = rot - Constants.R_2PI; // < 0
			return -rot > max_radians ? -max_radians : rot;
		}
		return rot > max_radians ? max_radians : rot;
	}
}
