<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
	<head>
		<title>HR Energize</title>
		<link rel="stylesheet" type="text/css" href="/stylesheets/opmaak.css"/>
	</head>
	<body>
		<div id="wrapper">
			<div id="header">
					<h2 id="title">
						Hogeschool Rotterdam - HR Energize
					</h2>
					<img id="headerimage" src="/images/hrlogo.jpg"/>
			</div>
			<div id="main" style="clear:both;">
				<%
					DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
					Query query = new Query("Pand").addSort("gebouw", Query.SortDirection.ASCENDING);
					List<Entity> panden = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(14));

					for(int i = 0; i <panden.size(); i++){
					Entity pand = panden.get(i);
					long status = (Long) pand.getProperty("status");
					String message = null;
					
				%>
					<div id="locatie">
						<%if(status == 0){ %>
							<img class="stoplicht" src="/images/stoplicht_uit_middel.png" />
							<%message = "N/A"; %>
						<%}if(status == 1){ %>
							<img class="stoplicht" src="/images/stoplicht_groen_middel.png" />
							<%message = "in orde"; %>
						<%}else if(status == 2){ %>
							<img class="stoplicht" src="/images/stoplicht_oranje_middel.png" />
							<%message = "redelijk"; %>
						<%}else if(status == 3){%>
							<img class="stoplicht" src="/images/stoplicht_rood_middel.png" />
							<%message = "ALARM!"; %>
						<%} %>
						<table class="font12">
							<tr>
								<th colspan="2"><%=  pand.getProperty("gebouw")  %></th>
							</tr>
							<tr>
								<td>geschat:</td>
								<td><%= pand.getProperty("Ggister") %> kWh</td>
							</tr>
							<tr>
								<td>gemeten:</td>
								<td><%= pand.getProperty("meting") %> kWh</td>
							</tr>
							<tr>
								<td>status:</td>
								<td><%= message %></td>
							</tr>
							<tr>
								<td>opmerking:</td>
								<td><%= pand.getProperty("opmerking") %></td>
							</tr>
						</table>
					</div>
					<%} %>
			</div>
		</div>
	</body>
</html>