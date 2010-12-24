<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>My Google API Application</title>
    <script src="https://www.google.com/jsapi?key=ABQIAAAA8B3kWBNcSBdYgqOALe6CrhTwM0brOpm-All5BF6PoaKBxRWWERRuBhh8sMpZkkwf5goj7Q8gxnU1fQ" type="text/javascript"></script>
    <script type="text/javascript">
    //<![CDATA[

    google.load("search", "1");

    function OnLoad() {
      // Create a search control
      var searchControl = new google.search.SearchControl();

      // Add in a full set of searchers
      siteSearch = new google.search.WebSearch();
      siteSearch.setUserDefinedLabel("www.themoviespoiler.com");
      siteSearch.setSiteRestriction("themoviespoiler.com");
      
      options = new google.search.SearcherOptions();
      options.setExpandMode(google.search.SearchControl.EXPAND_MODE_OPEN);

      searchControl.addSearcher(siteSearch,options);
            
      // Tell the searcher to draw itself and tell it where to attach
      searchControl.draw(document.getElementById("searchcontrol"));

    }
    google.setOnLoadCallback(OnLoad);

    //]]>
    </script>
  </head>
  <body>
    <div id="searchcontrol">Loading...</div>
  </body>
</html>