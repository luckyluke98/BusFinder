# A Heuristic for Minimizing Transfers in BusRoutes Modeled with GTFS

You will find the explanation of the underlying idea in the report within the project [here](https://github.com/luckyluke98/BusFinder/blob/master/A_Heuristic_for_Minimizing_Transfers_in_Bus_Routes_Modeled_with_GTFS__English_.pdf):
 - _A_Heuristic_for_Minimizing_Transfers_in_Bus_Routes_Modeled_with_GTFS__English_.pdf

The GTFS format was not designed for directly handling complex calculations like minimizing transfers or calculating shortest paths, and it is also limited by its mostly static representation of data. To address this issue, a heuristic has been developed that, using GTFS data, allows for calculating routes with the minimum number of transfers needed to reach the destination. This heuristic, through a series of criteria and approximations, reduces the computational load of the algorithm by limiting the exploration to the most promising routes. The described approach represents a balance between accuracy and complexity, allowing for efficient management of optimized route calculations.

## Configuration

To test the heuristic, a sort of API was developed. To run tests, download ACTV's open data [here](https://actv.avmspa.it/sites/default/files/attachments/opendata/automobilistico/) (or any GTFS model). Download the `.txt` GTFS files and place them in a folder of your choice. Then, specify this folder and the filenames in the configuration file.

In `db_loader.properites`:
```
dbloader.config.input-path=<path to file>

dbloader.config.input.agency-file-name=\\agency.txt
dbloader.config.input.calendar-file-name=\\calendar.txt
dbloader.config.input.route-file-name=\\routes.txt
dbloader.config.input.shape-file-name=\\shapes.txt
dbloader.config.input.stop-file-name=\\stops.txt
dbloader.config.input.stoptimes-file-name=\\stop_times.txt
dbloader.config.input.trip-file-name=\\trips.txt
```

To load data from the `.txt` files into the database for the first time, a loader was developed. Therefore, on the first run, set the following properties in the `application.properties` file:
```
spring.jpa.hibernate.ddl-auto=create
spring.batch.job.enabled=false
spring.batch.jdbc.initialize-schema=always
```
and the following in the `db_loader.properties` file:
```
dbloader.config.load=true
```
as soon as the application starts, the database will be loaded, which will take some time. A faster loading approach could be implemented, but I didn't feel like it, lol.

## Usage
Once everything is configured and the app is running, the following endpoint will be available:

```
http://localhost:8080/algo?start=4594&end=461&deptime=12:00:00
```

The endpoint takes three parameters:
| Parameter      | Description                       |
|----------------|-----------------------------------|
| **start**      | ID of the departure stop  |
| **end**        | ID of the destination stop |
| **Ddeptime**   |Departure time in the format **HH:MM:SS** |

The application will return the optimized route with the minimum number of transfers. The return format is as follows:

```
{
  "steps": [

    /* First Line */
    {
      "route": {...},
      "stopTimesPair": {
        "src": {...},
        "dst": {...},
        "srcName": "Faro Rocchetta",
        "dstName": "Santa Maria Elisabetta"
      }
    },

    /* Second Line */
    {
      "route": {...},
      "stopTimesPair": {
        "src": {...},
        "dst": {...},
        "srcName": "Santa Maria Elisabetta",
        "dstName": "San Nicolo' Chiesa"
      }
    }
  ]
}
```

The example shows a case where the result is a route with one transfer. The route is divided into "steps," which contain information about each transfer. For example, the first step will describe the journey to take on the same line, from one stop to another, from the departure stop to the destination. The second step, on the other hand, will indicate the route from the previous destination stop (now considered the departure stop) to the new destination stop, but on a different line. Each object will contain information related to the line or stop.

A complete example is the following, which is the output of the previous query:

```
{
  "steps": [
    {
      "route": {
        "routeId": 21,
        "agencyId": "ACTVs.p.a",
        "routeShortName": "A",
        "routeLongName": "Faro Rocchetta - Santa Maria Elisabetta",
        "routeDesc": "UL",
        "routeType": "3",
        "routeUrl": "",
        "routeColor": "EF7F1A",
        "routeTextColor": "FFFFFF"
      },
      "stopTimesPair": {
        "src": {
          "id": {
            "tripId": 4067,
            "stopId": 4594,
            "stopSeq": 1
          },
          "arrivalTime": "12:13:00",
          "departureTime": "12:13:00",
          "stopHeadsign": "S.M.E.",
          "pickupType": 0,
          "dropOffType": 1,
          "shapeDistTraveled": ""
        },
        "dst": {
          "id": {
            "tripId": 4067,
            "stopId": 4641,
            "stopSeq": 28
          },
          "arrivalTime": "12:32:00",
          "departureTime": "12:32:00",
          "stopHeadsign": "S.M.E.",
          "pickupType": 1,
          "dropOffType": 0,
          "shapeDistTraveled": ""
        },
        "srcName": "Faro Rocchetta",
        "dstName": "Santa Maria Elisabetta"
      }
    },
    {
      "route": {
        "routeId": 10,
        "agencyId": "ACTVs.p.a",
        "routeShortName": "A",
        "routeLongName": "Santa Maria Elisabetta - Santa Maria Elisabetta",
        "routeDesc": "UL",
        "routeType": "3",
        "routeUrl": "",
        "routeColor": "EF7F1A",
        "routeTextColor": "FFFFFF"
      },
      "stopTimesPair": {
        "src": {
          "id": {
            "tripId": 4068,
            "stopId": 4641,
            "stopSeq": 1
          },
          "arrivalTime": "12:41:00",
          "departureTime": "12:41:00",
          "stopHeadsign": "S.M.E.",
          "pickupType": 0,
          "dropOffType": 1,
          "shapeDistTraveled": ""
        },
        "dst": {
          "id": {
            "tripId": 4068,
            "stopId": 461,
            "stopSeq": 14
          },
          "arrivalTime": "12:53:00",
          "departureTime": "12:53:00",
          "stopHeadsign": "S.M.E.",
          "pickupType": 0,
          "dropOffType": 0,
          "shapeDistTraveled": ""
        },
        "srcName": "Santa Maria Elisabetta",
        "dstName": "San Nicolo' Chiesa"
      }
    }
  ]
}
```

## Conclusion







