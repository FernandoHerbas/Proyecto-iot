document.addEventListener('DOMContentLoaded', function () {
    var myChart = Highcharts.chart('container', {
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
            type: 'datetime'
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
            data: [11.2, 23.2, 44.4, 10.3]
    
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