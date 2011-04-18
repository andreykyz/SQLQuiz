package ru.devhead.SQLQuiz.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
//import com.google.gwt.core.client.JsArray;
//import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class ValidatorGui implements EntryPoint {

	private int jsonRequestId = 0;//callback id
	
	private static final String JSON_BASE_URL = GWT.getModuleBaseURL();//"http://vz.devhead.ru:7080/";
	private static final String JSON_URL = "http://192.168.0.102/index.html";//JSON_BASE_URL + "getqueryresult?q=";

	// Компоненты дизайна
	HorizontalPanel upperPanel = new HorizontalPanel();
	VerticalPanel buttonPanel = new VerticalPanel();
	VerticalPanel mainPanel = new VerticalPanel();
	HorizontalPanel tablePanel = new HorizontalPanel();

	Button executeButton = new Button("Execute query");
	Button getResultButton = new Button("Get result");

	TextArea queryPlace = new TextArea();

	Label queryResult = new Label();
	Label correctQueryResult = new Label();

	FlexTable tableQueryResult = new FlexTable();
	FlexTable tableSolution = new FlexTable();

	String[][] tableModelQueryResult = {{"rrrr","Rrrr"},{"gdfgf","gdgdf"}};

	@Override
	public void onModuleLoad() {

		getResultButton.addStyleName("getResultButton");

		buttonPanel.add(executeButton);
		buttonPanel.add(getResultButton);
		buttonPanel.setStyleName("buttonPanel");

		queryPlace.setCharacterWidth(100);
		queryPlace.setVisibleLines(5);

		upperPanel.add(queryPlace);
		upperPanel.add(buttonPanel);

		tableQueryResult.setText(0, 0, "data00");

		tablePanel.add(tableQueryResult);
		tablePanel.add(tableSolution);

		tableQueryResult.addStyleName("flexTable");

		tableSolution.setText(4, 4, "4");
		mainPanel.add(upperPanel);
		mainPanel.add(tablePanel);

		RootPanel.get("validator").add(mainPanel);

		queryPlace.setFocus(true);

		executeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
//				loadTableModelQueryResult("[[\"f\",\"d\",\"g\"],[\"ads\",\"fsdf\",\"fsdf\"],[\"5345345\",\"45345345\",\"534534\"]]" );
//				loadTableModelQueryResult(asNORMAL_ARRAY("[[\"f\",\"d\",\"g\"],[\"ads\",\"fsdf\",\"fsdf\"],[\"5345345\",\"45345345\",\"534534\"]]").getThis());
				executeButtonClick();
			}

		});
		executeButtonClick();
	}

	void executeButtonClick() {

		String url = JSON_URL;//URL.encode(JSON_URL + queryPlace.getText());
		getJson(jsonRequestId++, url, this);

	}

	void loadTableModelQueryResult(String[][] parseLenient) {
		tableModelQueryResult = parseLenient;
		fireTableQueryResultUpdate();
	}

	void fireTableQueryResultUpdate() {

		tableQueryResult.clear(true);
		for (int i = 0; (i < tableModelQueryResult.length)
				& (tableModelQueryResult != null); i++) {
			for (int j = 0; (j < tableModelQueryResult[0].length)
					& (tableModelQueryResult[0] != null); j++) {
				tableQueryResult.setText(i, j, tableModelQueryResult[i][j]);
				if (i == 0) {
					// Стиль для шапки таблицы
					tableQueryResult.getRowFormatter().addStyleName(0,
							"flexTableHeader");
				} else {
					// Стиль для данных таблицы
					tableQueryResult.getCellFormatter().addStyleName(i, j,
							"flexTable");
				}
			}
		}
	}
	
	void displayError(String er) {
		tableModelQueryResult = new String[0][0];
		tableModelQueryResult[0][0] = er;
		
		fireTableQueryResultUpdate();
	}
	
	void fireTableSolutionUpdate() {
		// Заглушка
		tableQueryResult.clear(true);
	}

	/**
	 * Convert the string of JSON into JavaScript object.
	 */
	private final native JSONRequester asNORMAL_ARRAY(JavaScriptObject json) /*-{
	 return eval(json);
	 }-*/;

	/**
	 * Make call to remote server.
	 */
	public native static void getJson(int requestId, String url,
			ValidatorGui handler) /*-{
	   var callback = "callback125";

	   // [1] Create a script element.
	   var script = document.createElement("script");
	   script.setAttribute("src", url);
	   script.setAttribute("type", "text/javascript");

	   // [2] Define the callback function on the window object.
	   window[callback] = function(jsonObj) {
	   // [3]
	     handler.@ru.devhead.SQLQuiz.client.ValidatorGui::handleJsonResponse(Lcom/google/gwt/core/client/JavaScriptObject;)(jsonObj);
	     window[callback + "done"] = true;
	   }

	   // [4] JSON download has 1-second timeout.
	   setTimeout(function() {
	     if (!window[callback + "done"]) {
	       handler.@ru.devhead.SQLQuiz.client.ValidatorGui::handleJsonResponse(Lcom/google/gwt/core/client/JavaScriptObject;)(null);
	     }

	     // [5] Cleanup. Remove script and callback elements.
	     document.body.removeChild(script);
	     delete window[callback];
	     delete window[callback + "done"];
	   }, 1000);

	   // [6] Attach the script element to the document body.
	   document.body.appendChild(script);
	  }-*/;

	/**
	 * Handle the response to the request for stock data from a remote server.
	 */
	public void handleJsonResponse(JavaScriptObject jso) {
		if (jso == null) {
			displayError("Couldn't retrieve JSON");
			return;
		}
		String ggg = jso.toString();
		GWT.log(ggg);
		loadTableModelQueryResult( asNORMAL_ARRAY(jso).getDataTable());

	}

}
