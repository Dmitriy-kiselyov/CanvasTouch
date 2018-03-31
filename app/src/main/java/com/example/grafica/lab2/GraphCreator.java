package com.example.grafica.lab2;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

/**
 * Created by Sex_predator on 02.03.2016.
 */
public class GraphCreator {

    private static GraphView.Graph mGraph6, mGraph8, mGraph10, mGraph12;

    public static GraphView.Graph createGraph6() {
        if (mGraph6 == null)
            mGraph6 = new GraphView.Graph() {
                @Override
                public int getPointCount() {
                    return 600;
                }

                @Override
                public double getStartInterval() {
                    return 0;
                }

                @Override
                public double getEndInterval() {
                    return 10 * Math.PI;
                }

                @Override
                public double interpolateX(double t) {
                    return 24.8 * (cos(t) + cos(6.2 * t) / 6.2);
                }

                @Override
                public double interpolateY(double t) {
                    return 24.8 * (sin(t) - sin(6.2 * t) / 6.2);
                }
            };
        return mGraph6;
    }

    public static String getGraph6Name() {
        return "Graph #6";
    }

    public static GraphView.Graph createGraph8() {
        if (mGraph8 == null)
            mGraph8 = new GraphView.Graph() {
                @Override
                public int getPointCount() {
                    return 150;
                }

                @Override
                public double getStartInterval() {
                    return 0;
                }

                @Override
                public double getEndInterval() {
                    return 2 * Math.PI;
                }

                @Override
                public double interpolateX(double t) {
                    return 6 * cos(t) - 4 * pow(cos(t), 3);
                }

                @Override
                public double interpolateY(double t) {
                    return 4 * pow(sin(t), 3);
                }
            };
        return mGraph8;
    }

    public static String getGraph8Name() {
        return "Graph #8";
    }

    public static GraphView.Graph createGraph10() {
        if (mGraph10 == null)
            mGraph10 = new GraphView.Graph() {
                @Override
                public int getPointCount() {
                    return 1000;
                }

                @Override
                public double getStartInterval() {
                    return 0;
                }

                @Override
                public double getEndInterval() {
                    return 20 * Math.PI;
                }

                @Override
                public double interpolateX(double t) {
                    return 6.2 * (cos(t) - cos(3.1 * t) / 3.1);
                }

                @Override
                public double interpolateY(double t) {
                    return 6.2 * (sin(t) - sin(3.1 * t) / 3.1);
                }
            };
        return mGraph10;
    }

    public static String getGraph10Name() {
        return "Graph #10";
    }

    public static GraphView.Graph createGraph12() {
        if (mGraph12 == null)
            mGraph12 = new GraphView.Graph() {
                @Override
                public int getPointCount() {
                    return 1200;
                }

                @Override
                public double getStartInterval() {
                    return 0;
                }

                @Override
                public double getEndInterval() {
                    return 12 * Math.PI;
                }

                @Override
                public double interpolateX(double t) {
                    return sin(t) *
                           (pow(Math.E, cos(t)) - 2 * cos(4 * t) + pow(sin(1.0 / 12 * t), 5));
                }

                @Override
                public double interpolateY(double t) {
                    return cos(t) *
                           (pow(Math.E, cos(t)) - 2 * cos(4 * t) + pow(sin(1.0 / 12 * t), 5));
                }
            };
        return mGraph12;
    }

    public static String getGraph12Name() {
        return "Graph #12";
    }

}
