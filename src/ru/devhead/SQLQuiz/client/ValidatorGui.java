package ru.devhead.SQLQuiz.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
//import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.*;

public class ValidatorGui implements EntryPoint {

	private static final String JSON_BASE_URL = GWT.getModuleBaseURL();//"http://vz.devhead.ru:7080/";
	private static final String JSON_URL = "http://192.168.0.102";//JSON_BASE_URL + "getqueryresult?q=";

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

	String[][] tableModelQueryResult;

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

				executeButtonClick();
			}

		});
	}

	void executeButtonClick() {

		String url = JSON_URL;//URL.encode(JSON_URL + queryPlace.getText());

		// Send request to server and catch any errors.
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		try {
			@SuppressWarnings("unused")
			Request request = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					displayError("Couldn't retrieve JSON");
				}

				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
//						loadTableModelQueryResult(JSONParser.parseLenient(response.getText()));
						displayError("200");
//						updateTable(asArrayOfStockData(response.getText()));
					} else {
						displayError("Couldn't retrieve JSON ("
								+ response.getStatusText() + ")");
						
					}
				}
			});
		} catch (RequestException e) {
			displayError("Couldn't retrieve JSON");
		}

	}

	void loadTableModelQueryResult(JSONValue parseLenient) {
		JSONArray ar = (JSONArray)parseLenient;
		for (int i=0; i<ar.size();i++) {
			for (int j=0; j<((JSONArray)ar.get(i)).size();j++) {
				tableModelQueryResult[i][j] = ((JSONArray)ar.get(i)).get(j).toString();
			}
		}
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
//	 private final native JsArray asArrayOfStockData(String json) /*-{
//	 return eval(json);
//	 }-*/;

}
