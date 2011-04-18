package ru.devhead.SQLQuiz.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;


public class JSONRequester extends JavaScriptObject {
	
	protected JSONRequester() {}
	
	public final String[][] getDataTable() {
		JsArray<JsArrayString> dataTable = getThis();
		if ((dataTable != null) & (dataTable.get(0) != null)) {
			String[][] str = new String[dataTable.length()][(dataTable.get(0)).length()];
			for (int i = 0; i < dataTable.length(); i++) {
				for (int j = 0; j < (dataTable.get(0)).length(); j++) {
					str[i][j] = (dataTable.get(i)).get(j);
				}
			}
			return str;
		} else {
			return null;
		}
	}

	public final native JsArray<JsArrayString> getThis() /*-{ return this.datatable; }-*/;

}
