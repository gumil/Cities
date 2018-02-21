# Cities
Shows a list of 200k cities and its location in the map from a json file

## Source Link
https://github.com/gumil/Cities

## Solution
Steps:
1. Read `cities.json` by line
2. If line is valid, parse to line using `Gson`
3. Add `City` into `TreeMap` using city name and country as key

Since the json file is formatted where each line can be parsed to a city, the list of cities is processed by getting every line since it's safer to process the cities.json file that way. Reading in by buffer might cause `OutOfMemoryError`. Also reading by chunks can result to malformed json and might need to combine from different chunks.

Using `TreeMap`, it can eliminate duplicates and sort the map when inserting. Searching is also easier since it can get a submap based on prefix.
