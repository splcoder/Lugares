package com.example.lugares.helpers.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * It could be great take all from
 * 		http://www.netlib.org/cephes/
 * 		http://www.luschny.de/math/factorial/approx/SimpleCases.html
 *		http://keithbriggs.info/software/LambertW.c		and		http://keithbriggs.info/software/LambertW1.c
 */
public enum MathFunction {
	ABS, MOD, INV /* 1/x */, RAD /* to rad */, DEG /* to degrees*/, FACTORIAL
	, FLOOR, ROUND, CEIL
	, POW2, POW3, POW, ROOT2, ROOT3, ROOT, XPOWX, INV_XPOWX
	, EXP, LN, EXP10, LOG10, LOG
	, SIN, COS, TAN, ARCSIN, ARCCOS, ARCTAN
	, SINH, COSH, TANH, ARCSINH, ARCCOSH, ARCTANH
	, LAMBERTW, LAMBERTW_1
	, BY10POW, HYPOT, ATAN2
	;

	// TODO add Math remainder functions, and: Sum, Product, Integral

	// = MathFunction.values()
	public static final List<MathFunction> listFunctions = new ArrayList<MathFunction>(
		Arrays.asList(
			ABS, MOD, INV /* 1/x */, RAD /* to rad */, DEG /* to degrees*/, FACTORIAL
			, FLOOR, ROUND, CEIL
			, POW2, POW3, POW, ROOT2, ROOT3, ROOT, XPOWX, INV_XPOWX
			, EXP, LN, EXP10, LOG10, LOG
			, SIN, COS, TAN, ARCSIN, ARCCOS, ARCTAN
			, SINH, COSH, TANH, ARCSINH, ARCCOSH, ARCTANH
			, LAMBERTW, LAMBERTW_1
			, BY10POW, HYPOT, ATAN2
		)
	);

	public static boolean isOneArgumentFunction( MathFunction mf ){
		switch( mf ){
			case MOD:
			case POW:
			case ROOT:
			case LOG:
			case BY10POW:
			case HYPOT:
			case ATAN2:
				return false;
			default:;
		}
		return true;
	}

	public static String toString( MathFunction mf ){
		switch( mf ){
			case ABS:		return "abs";
			case MOD:		return "mod";
			case INV:		return "inv";
			case RAD:		return "rad";
			case DEG:		return "deg";
			case FACTORIAL:	return "factorial";
			case FLOOR:		return "floor";
			case ROUND:		return "round";
			case CEIL:		return "ceil";
			case POW2:		return "pow2";
			case POW3:		return "pow3";
			case POW:		return "pow";
			case ROOT2:		return "root2";
			case ROOT3:		return "root3";
			case ROOT:		return "root";
			case XPOWX:		return "xPowX";
			case INV_XPOWX:	return "invXpowX";
			case EXP:		return "exp";
			case LN:		return "ln";
			case EXP10:		return "exp10";
			case LOG10:		return "log10";
			case LOG:		return "log";
			case SIN:		return "sin";
			case COS:		return "cos";
			case TAN:		return "tan";
			case ARCSIN:	return "arcsin";
			case ARCCOS:	return "arccos";
			case ARCTAN:	return "arctan";
			case SINH:		return "sinh";
			case COSH:		return "cosh";
			case TANH:		return "tanh";
			case ARCSINH:	return "arcsinh";
			case ARCCOSH:	return "arccosh";
			case ARCTANH:	return "arctanh";
			case LAMBERTW:	return "lambertW";
			case LAMBERTW_1:return "lambertW_1";
			case BY10POW:	return "E";
			case HYPOT:		return "hypot";
			case ATAN2:		return "atan2";
		}
		return "";
	}

	public static double exeFunction( MathFunction mf, double arg1, double arg2 ){
		switch( mf ){
			case ABS:		return Math.abs( arg1 );
			case MOD:		return arg1 % arg2;
			case INV:		return 1./arg1;
			case RAD:		return arg1*Math.PI/180;
			case DEG:		return arg1*180/Math.PI;
			case FACTORIAL:	return arg1 < 0 ? reflectionFactorial( arg1 ) : factorial( arg1 );
			case FLOOR:		return Math.floor( arg1 );
			case ROUND:		return Math.round( arg1 );
			case CEIL:		return Math.ceil( arg1 );
			case POW2:		return arg1 * arg1;
			case POW3:		return arg1 * arg1 * arg1;
			case POW:		return Math.pow( arg1, arg2 );
			case ROOT2:		return Math.sqrt( arg1 );
			case ROOT3:		return Math.cbrt( arg1 );
			case ROOT:		return Math.pow( arg1, 1/arg2 );
			case XPOWX:		return Math.pow( arg1, arg1 );
			case INV_XPOWX:	return inverseXPowX( arg1 );
			case EXP:		return Math.exp( arg1 );
			case LN:		return Math.log( arg1 );
			case EXP10:		return Math.pow( 10, arg1 );
			case LOG10:		return Math.log10( arg1 );
			case LOG:		return Math.log( arg1 ) / Math.log( arg2 );
			case SIN:		return Math.sin( arg1 );
			case COS:		return Math.cos( arg1 );
			case TAN:		return Math.tan( arg1 );
			case ARCSIN:	return Math.asin( arg1 );
			case ARCCOS:	return Math.acos( arg1 );
			case ARCTAN:	return Math.atan( arg1 );
			case SINH:		return Math.sinh( arg1 );
			case COSH:		return Math.cosh( arg1 );
			case TANH:		return Math.tanh( arg1 );
			case ARCSINH:	return Math.log( arg1 + Math.sqrt( arg1*arg1 + 1.0 ) );
			case ARCCOSH:	return Math.log( arg1 + Math.sqrt( arg1*arg1 - 1.0 ) );
			case ARCTANH:	return 0.5*Math.log( (1.0 + arg1) / (1.0 - arg1) );
			case LAMBERTW:	return lambertW( arg1 );
			case LAMBERTW_1:return lambertW1( arg1 );
			case BY10POW:	return arg1 * Math.pow( 10, arg2 );
			case HYPOT:		return Math.hypot( arg1, arg2 );
			case ATAN2:		return Math.atan2( arg2, arg1 );
		}
		return 0;
	}
	public static double exeFunction( MathFunction mf, double arg1 ){
		return exeFunction( mf, arg1, 0 );
	}

	////////////////////////// FUNCTIONS ///////////////////////////////////////////////////////////

	private static final double[] FACTORIAL_LUSCHNY_CF4_CONSTANTS = { 1./24, 3./80, 18029./45360, 6272051./14869008 };
	private static double factorial( double arg ){
		//return Math.pow( arg, arg )*Math.exp( -arg )*Math.sqrt( arg*2*Math.PI + 1 );
		// Another approximation:	luschnyCF4
		double N = arg + 0.5;
		double p = N*N/( N + FACTORIAL_LUSCHNY_CF4_CONSTANTS[0]/( N + FACTORIAL_LUSCHNY_CF4_CONSTANTS[1]/( N + FACTORIAL_LUSCHNY_CF4_CONSTANTS[2]/( N + FACTORIAL_LUSCHNY_CF4_CONSTANTS[3]/N ) ) ) );
		double logF = Math.log( 2 * Math.PI )/2 + N*(Math.log( p ) - 1);
		// If arg is an integer then take round
		if( arg < 17 && Math.round( arg ) == arg )	return Math.round( Math.exp( logF ) );
		return Math.exp( logF );
	}
	private static double reflectionFactorial( double arg ){
		// arg < 0
		arg = -arg;
		double byPi = Math.PI * arg;
		return byPi/(Math.sin( byPi ) * factorial( arg ));
	}

	private static double lambertW( double z ){
		int i;
		final double eps = 4.0e-16, em1 = 0.3678794411714423215955237701614608;
		double p, e, t, w;
		if( Double.isInfinite( z ) && z > 0 )	return z;	// + INF
		if( z < -em1 || Double.isNaN( z ) ){
			return Double.NaN;
		}
		if( 0.0 == z )	return 0.0;
		if( z < -em1 + 1e-4 ){	// series near -em1 in sqrt(q)
			double q = z + em1, r = Math.sqrt( q ), q2 = q*q, q3 = q2*q;
			return			-1.0
							+2.331643981597124203363536062168*r
							-1.812187885639363490240191647568*q
							+1.936631114492359755363277457668*r*q
							-2.353551201881614516821543561516*q2
							+3.066858901050631912893148922704*r*q2
							-4.175335600258177138854984177460*q3
							+5.858023729874774148815053846119*r*q3
							-8.401032217523977370984161688514*q3*q;  // error approx 1e-16
		}
		// initial approx for iteration...
		if( z < 1.0 ){	// series near 0
			p = Math.sqrt( 2.0*(2.7182818284590452353602874713526625*z + 1.0) );
			w = -1.0 + p*(1.0 + p*(-0.333333333333333333333 + p*0.152777777777777777777777));
		}
		else	w = Math.log( z );	// asymptotic
		if( z > 3.0 )	w -= Math.log( w );	// useful ?
		for( i = 0; i < 10; i++ ){	// Halley iteration
			e = Math.exp( w );
			t = w*e - z;
			p = w + 1.0;
			t /= e*p - 0.5*(p + 1.0)*t/p;
			w -= t;
			if( Math.abs( t ) < eps*(1.0 + Math.abs( w )) )	return w;	// rel-abs error
		}
		// should never get here
		return w;
	}

	private static double lambertW1( double z ){
		int i;
		final double eps = 4.0e-16, em1 = 0.3678794411714423215955237701614608;
		double p = 1.0, e, t, w, l1, l2;
		if( z < -em1 || z >= 0.0 || Double.isInfinite( z ) || Double.isNaN( z ) ){
			return Double.NaN;
		}
		// initial approx for iteration...
		if( z < -1e-6 ){	// series about -1/e
			p = -Math.sqrt( 2.0*(2.7182818284590452353602874713526625*z + 1.0) );
			w = -1.0 + p*(1.0 + p*(-0.333333333333333333333 + p*0.152777777777777777777777));
		}
		else{	// asymptotic near zero
			l1 = Math.log( -z );
			l2 = Math.log( -l1 );
			w = l1 - l2 + l2/l1;
		}
		if( Math.abs( p ) < 1e-4 )	return w;
		for( i = 0; i < 10; i++ ){	// Halley iteration
			e = Math.exp( w );
			t = w*e - z;
			p = w + 1.0;
			t /= e*p - 0.5*(p + 1.0)*t/p;
			w -= t;
			if( Math.abs( t ) < eps*(1.0 + Math.abs( w )) )		return w;	// rel-abs error
		}
		// should never get here
		return w;
	}

	private static double inverseXPowX( double arg ){
		//return Math.exp( lambertW( Math.log( arg ) ) );
		double log = Math.log( arg );
		return log/lambertW( log );
	}
}
