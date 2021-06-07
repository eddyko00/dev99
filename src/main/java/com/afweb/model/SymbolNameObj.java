package com.afweb.model;

public class SymbolNameObj {

    private String NormalizeSymbol;
    private String symbolFileName;
    private String YahooSymbol;

    static String strReplace(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();

        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }

    public SymbolNameObj(String strSymbol) {
        strSymbol = strSymbol.trim().toUpperCase();
        String strSymbolName = strSymbol;
        int pos;
        if ((pos = strSymbolName.indexOf("CA:T.")) != -1) {
            strSymbolName = strSymbolName.substring(5);
            strSymbolName = "CA_T_" + strSymbolName;
            strSymbolName = strReplace(strSymbolName, ".", "-");
            this.symbolFileName = strSymbolName;
            this.NormalizeSymbol = strSymbol;
        } else if ((pos = strSymbolName.indexOf("CA_T_")) != -1) {
            // check file name
            strSymbolName = strSymbolName.substring(5);
            strSymbolName = "CA:T." + strSymbolName;

            this.NormalizeSymbol = strSymbolName;
            this.symbolFileName = strSymbol;

        } else if ((pos = strSymbolName.indexOf("SS:T.")) != -1) {
            strSymbolName = strSymbolName.substring(5);
            strSymbolName = "SS_T_" + strSymbolName;
            strSymbolName = strReplace(strSymbolName, ".", "-");
            this.symbolFileName = strSymbolName;
            this.NormalizeSymbol = strSymbol;
        } else if ((pos = strSymbolName.indexOf("SS_T_")) != -1) {
            // check file name
            strSymbolName = strSymbolName.substring(5);
            strSymbolName = "SS:T." + strSymbolName;

            this.NormalizeSymbol = strSymbolName;
            this.symbolFileName = strSymbol;

        } else if ((pos = strSymbolName.indexOf("SS:HK.")) != -1) {
            strSymbolName = strSymbolName.substring(6);
            strSymbolName = "SS_HK_" + strSymbolName;
            strSymbolName = strReplace(strSymbolName, ".", "-");
            this.symbolFileName = strSymbolName;
            this.NormalizeSymbol = strSymbol;
        } else if ((pos = strSymbolName.indexOf("SS_HK_")) != -1) {
            // check file name
            strSymbolName = strSymbolName.substring(6);
            strSymbolName = "SS:HK." + strSymbolName;

            this.NormalizeSymbol = strSymbolName;
            this.symbolFileName = strSymbol;

        } else if ((pos = strSymbolName.indexOf("SS:")) != -1) {
            strSymbolName = strSymbolName.substring(3);
            strSymbolName = "SS_" + strSymbolName;
            strSymbolName = strReplace(strSymbolName, ".", "-");
            this.symbolFileName = strSymbolName;
            this.NormalizeSymbol = strSymbol;
        } else if ((pos = strSymbolName.indexOf("SS_")) != -1) {
            // check file name
            strSymbolName = strSymbolName.substring(3);
            strSymbolName = "SS:" + strSymbolName;

            this.NormalizeSymbol = strSymbolName;
            this.symbolFileName = strSymbol;
        } else if ((pos = strSymbolName.indexOf(".TO")) != -1) {
            strSymbolName = strReplace(strSymbolName, ".TO", "");
            this.NormalizeSymbol = "CA:T." + strSymbolName;
            strSymbolName = "CA_T_" + strSymbolName;
            strSymbolName = strReplace(strSymbolName, ".", "-");
            this.symbolFileName = strSymbolName;
        } else if ((pos = strSymbolName.indexOf("_TO")) != -1) {
            strSymbolName = strReplace(strSymbolName, "_TO", "");
            this.NormalizeSymbol = "CA:T." + strSymbolName;
            strSymbolName = "CA_T_" + strSymbolName;
            strSymbolName = strReplace(strSymbolName, ".", "-");
            this.symbolFileName = strSymbolName;

        } else if ((pos = strSymbolName.indexOf(".HK")) != -1) {
            strSymbolName = strReplace(strSymbolName, ".HK", "");
            this.NormalizeSymbol = "HK:" + strSymbolName;
            strSymbolName = "HK_" + strSymbolName;
            strSymbolName = strReplace(strSymbolName, ".", "-");
            this.symbolFileName = strSymbolName;
        } else if ((pos = strSymbolName.indexOf("HK:")) != -1) {
            strSymbolName = strSymbolName.substring(3);
            strSymbolName = "HK_" + strSymbolName;
            strSymbolName = strReplace(strSymbolName, ".", "-");
            this.symbolFileName = strSymbolName;

            this.NormalizeSymbol = strSymbol;
        } else if ((pos = strSymbolName.indexOf("HK_")) != -1) {
            // check file name
            strSymbolName = strSymbolName.substring(3);
            strSymbolName = "HK:" + strSymbolName;

            this.NormalizeSymbol = strSymbolName;
            this.symbolFileName = strSymbol;

        } else {
            strSymbolName = strReplace(strSymbolName, ".", "-");
            this.NormalizeSymbol = strSymbol;
            this.symbolFileName = strSymbol;
        }
        YahooSymbol = setYahooSymbol();
    }

    //Assume input is CA:T.RCI.B
    //Yahoo - RCI-B.TO
    //MSN   - CA:RCI.B
    private String setYahooSymbol() {
        String strSymbolName = NormalizeSymbol;
        int pos;
        if ((pos = strSymbolName.indexOf("CA:T.")) != -1) {
            strSymbolName = strSymbolName.substring(5);
            strSymbolName = strReplace(strSymbolName, ".", "-");
            return strSymbolName + ".TO";
        } else if ((pos = strSymbolName.indexOf("SS:T.")) != -1) {
            strSymbolName = strSymbolName.substring(5);
            strSymbolName = strReplace(strSymbolName, ".", "-");
            return strSymbolName + ".TO";
        } else if ((pos = strSymbolName.indexOf("SS:HK.")) != -1) {
            strSymbolName = strSymbolName.substring(6);
            strSymbolName = strReplace(strSymbolName, ".", "-");
            return strSymbolName + ".HK";
            //
            // SS: or HK:: must be at the last
            //
        } else if ((pos = strSymbolName.indexOf("SS:")) != -1) {
            strSymbolName = strSymbolName.substring(3);
            strSymbolName = strReplace(strSymbolName, ".", "-");
            return strSymbolName + "";
        } else if ((pos = strSymbolName.indexOf("HK:")) != -1) {
            strSymbolName = strSymbolName.substring(3);
            strSymbolName = strReplace(strSymbolName, ".", "-");
            return strSymbolName + ".HK";

        }

        strSymbolName = strReplace(strSymbolName, ".", "-");
        return strSymbolName;
    }

    public static String getMSNSymbol(String normalizeSymbol) {
        String msnSymbol = normalizeSymbol.trim().toUpperCase();
        msnSymbol = msnSymbol.replace("CA:T.", "CA:");
        msnSymbol = msnSymbol.replace("SS:T.", "CA:");
        msnSymbol = msnSymbol.replace("SS:", "");
        msnSymbol = msnSymbol.replace("SS:HK.", "");
        msnSymbol = strReplace(msnSymbol, "-", ".");
        return msnSymbol;
    }

    public String getYahooSymbol() {
        return YahooSymbol;
    }

    public String getSymbol() {
        return NormalizeSymbol;
    }

    public String getSymbolFileName() {
        return symbolFileName;
    }

    public static String convertGlobeFundSymbol(String strSymbol) {
        strSymbol = strSymbol.trim().toUpperCase();
        int TagPos;
        if ((TagPos = strSymbol.indexOf("-T")) != -1) {
            strSymbol = strReplace(strSymbol, ".", "-");
            strSymbol = "CA:T." + strSymbol.substring(0, TagPos);
            return strSymbol;
        } else if ((TagPos = strSymbol.indexOf("-N")) != -1) {
            strSymbol = strSymbol.substring(0, TagPos);
        } else if ((TagPos = strSymbol.indexOf("-A")) != -1) {
            strSymbol = strSymbol.substring(0, TagPos);
        } else if ((TagPos = strSymbol.indexOf("-Q")) != -1) {
            strSymbol = strSymbol.substring(0, TagPos);
        } else if ((TagPos = strSymbol.indexOf("-QS")) != -1) {
            strSymbol = strSymbol.substring(0, TagPos);
        }
        strSymbol = strReplace(strSymbol, ".", "-");
        return strSymbol;
    }
}
