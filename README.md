# A Heuristic for Minimizing Transfers in BusRoutes Modeled with GTFS

You will find the explanation of the underlying idea in the report within the project.

## How to use it?

Download the .txt GTFS files and place them in a folder of your choice. Then, specify this folder and the filenames in the configuration file.
```
...
dbloader.config.input-path=<path to file>
...

dbloader.config.input.agency-file-name=\\agency.txt
dbloader.config.input.calendar-file-name=\\calendar.txt
dbloader.config.input.route-file-name=\\routes.txt
dbloader.config.input.shape-file-name=\\shapes.txt
dbloader.config.input.stop-file-name=\\stops.txt
dbloader.config.input.stoptimes-file-name=\\stop_times.txt
dbloader.config.input.trip-file-name=\\trips.txt
```

Within the configuration files, you can set up the loading of files into the database and various other features.

Se si sta caricando per la prima volta i dati dai txt settare le seguenti proprietà:
```
dbloader.config.load=true
...
spring.batch.job.enabled=false

spring.batch.jdbc.initialize-schema=always
```
