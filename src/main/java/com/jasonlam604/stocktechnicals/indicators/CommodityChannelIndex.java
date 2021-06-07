package com.jasonlam604.stocktechnicals.indicators;

import com.jasonlam604.stocktechnicals.util.NumberFormatter;

/**
 * Commodity Channel Index
 */
public class CommodityChannelIndex {

	private double[] cci;

	public CommodityChannelIndex calculate(double[] high, double[] low, double[] close, int range) throws Exception {

		TypicalPrice typicalPrice = new TypicalPrice();
		double[] tp = typicalPrice.calculate(high, low, close).getTypicalPrice();

		SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage();
		double[] sma = simpleMovingAverage.calculate(tp, range).getSMA();

		this.cci = new double[high.length];

		double[] meanDev = new double[high.length];

		double sum = 0;
		double meanDeviation = 0;

		for (int i = (range - 1); i < close.length; i++) {

			sum = 0;
			meanDeviation = 0;

			for (int j = (i - range + 1); j < (i + 1); j++) {
				sum += Math.abs(tp[j] - sma[i]);
			}

			meanDeviation = sum / range;

			meanDev[i] = meanDeviation;

			if (meanDeviation > 0) {
				this.cci[i] = NumberFormatter.round((tp[i] - sma[i]) / (0.015 * meanDeviation));
			}

		}

		return this;
	}

	public double[] getCCI() {
		return this.cci;
	}

}
