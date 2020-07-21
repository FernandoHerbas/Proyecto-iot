<?php

    $obj_conexion = iniciarConexion("root","42737740","pruebasDomotica","localhost");
    $obj_conexion->set_charset("utf8");
   
    /*
    $temp = $_POST['temp'];
    $serie = $_POST['serie'];
    $sql = "INSERT INTO `mediciones` (`id`, `fecha`, `Temperatura`, `serie`) VALUES (NULL,CURRENT_TIMESTAMP, '$temp', '$serie' )";
    $datosDeInsercion = $obj_conexion->query($sql);
    if(!$datosDeInsercion){
        echo "<h3>No se ha podido enviar los datos.</h3><hr><br>";
    }
*/
    function obtenerTemperaturaDeSQL($serie,$intervalo,$mes,$dia,$obj_conexion){
        //Funcion que me devuelve el año actual?
        $anio = date(Y);

        //Obtengo los valores de una determinada fecha
        $valor =  "SELECT UNIX_TIMESTAMP(fecha), temperatura FROM mediciones WHERE 
                                                                             year(`fecha`) = '$anio' AND
                                                                             month(`fecha`)= '$mes'  AND
                                                                             day(`fecha`)  = '$dia'  AND
                                                                             `serie`       = '$serie')";
        $lecturaTotal = $obj_conexion->query($valor);
        while($fila = mysql_fetch_array($lecturaParcial)){
            echo "[";
            echo $fila[0]*1000;
            echo ",";
            echo $fila[1];
            echo "],";
        }

    }


    function iniciarConexion($usuario,$pass,$baseDeDatos,$servidor){
        $obj_conexion = new mysqli($servidor,$usuario,$pass,$baseDeDatos);
        if(!$obj_conexion)
        {
            echo "<h3>No se ha podido conectar PHP - MySQL, verifique sus datos.</h3><hr><br>";
            exit;
        }
        return $obj_conexion;
    }
?>
<head>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script src="https://code.highcharts.com/modules/export-data.js"></script>
    <script src="https://code.highcharts.com/modules/accessibility.js"></script>
</head>

    <figure class="highcharts-figure">
                <div id="container" style="width:100%; height:400px;"></div>
                    <p class="highcharts-description">
                    
                    This chart shows how data labels can be added to the data series. This
                    can increase readability and comprehension for small datasets.
                    </p>
    </figure>

<script>
    var myChart;
    document.addEventListener('DOMContentLoaded', function () {
    myChart = Highcharts.chart('container', {
        chart: {
            type: 'spline'
        },
        title: {
            text: 'Proyecto domotica'
        },
        subtitle: {
            text: 'xD'
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Temperatura del día'
            },
            labels: {
                formatter: function () {
                    return this.value + '°';
                }
            }
        },
        tooltip: {
            crosshairs: true,
            shared: true
        },
        plotOptions: {
            spline: {
                marker: {
                    radius: 4,
                    lineColor: '#666666',
                    lineWidth: 1
                }
            }
        },
        series: [{
            name: 'DHT11',
            marker: {
                symbol: 'square'
            },
            data: [<?php obtenerTemperaturaDeSQL("LM35s","0","7","20",$obj_conexion);?>]
    
        }, {
            name: 'London',
            marker: {
                symbol: 'diamond'
            },
            data: [{
                y: 3.9,
                marker: {
                    symbol: 'url(https://www.highcharts.com/samples/graphics/snow.png)'
                }
            }, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.0, 6.6, 4.8]
        }]
    });
    });
</script>

<style>
    .highcharts-figure, .highcharts-data-table table {
    min-width: 310px; 
    max-width: 800px;
    margin: 1em auto;
}

.highcharts-data-table table {
	font-family: Verdana, sans-serif;
	border-collapse: collapse;
	border: 1px solid #EBEBEB;
	margin: 10px auto;
	text-align: center;
	width: 100%;
	max-width: 500px;
}
.highcharts-data-table caption {
    padding: 1em 0;
    font-size: 1.2em;
    color: #555;
}
.highcharts-data-table th {
	font-weight: 600;
    padding: 0.5em;
}
.highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {
    padding: 0.5em;
}
.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even) {
    background: #f8f8f8;
}
.highcharts-data-table tr:hover {
    background: #f1f7ff;
}
</style>