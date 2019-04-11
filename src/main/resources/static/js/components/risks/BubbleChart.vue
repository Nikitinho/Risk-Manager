<script>
    // Useful site with samples: www.sitepoint.com/creating-beautiful-charts-vue-chart-js
    //Importing Bubble class from the vue-chartjs wrapper
    import { Bubble } from 'vue-chartjs'
    import Risk from 'domain/Risk'
    //Exporting this so it can be used in other components
    export default {
        extends: Bubble,
        props: ['data'],
        data () {
            return {
                datacollection: {
                    //Data to be represented on x-axis
                    labels: 'Threats',
                    datasets: [
                    ]
                },
                //Chart.js options that controls the appearance of the chart
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            },
                            gridLines: {
                                display: true
                            }
                        }],
                        xAxes: [ {
                            gridLines: {
                                display: false
                            }
                        }]
                    },
                    tooltips: {
                        callbacks: {
                            label(tooltipItem, data) {
                                const label = data.datasets[tooltipItem.datasetIndex].label
                                const value = Number(tooltipItem.yLabel) * Number(tooltipItem.xLabel)

                                return `${label} ${value}`
                            }
                        }
                    },
                    legend: {
                        display: true
                    },
                    responsive: true,
                    maintainAspectRatio: false
                }
            }
        },
        created () {
            this.addData(this.data)
        },
        mounted () {
            //renderChart function renders the chart with the datacollection and options object.
            this.renderChart(this.datacollection, this.options)
        },
        methods: {
            addData(data) {
                for (let element of data) {
                    if (!element.probability || !element.impact) { continue; }
                    this.addNewBubble(element)
                }
                this.addGraphPerimeter(10, 10)
            },
            addNewBubble(element) {
                let newBubble = {
                    label: element.text,
                    backgroundColor: Risk.convertRiskRateToColor(element.probability * element.impact),
                    pointBackgroundColor: 'white',
                    borderWidth: 2,
                    pointBorderColor: '#000000',
                    data: [
                        {
                            x: element.probability,
                            y: element.impact,
                            r: 25
                        }
                    ]
                }
                this.datacollection.datasets.push(newBubble)
            },
            addGraphPerimeter(xMax, yMax){
                let newPerimeter = {
                    label: 'End of graph',
                    backgroundColor: 'white',
                    pointBackgroundColor: 'white',
                    borderWidth: 1,
                    pointBorderColor: '#464949',
                    //Data to be represented on y-axis
                    data: [
                        {
                            x: xMax,
                            y: 0,
                            r: 0
                        },
                        {
                            x: 0,
                            y: yMax,
                            r: 0
                        }
                    ]
                }
                this.datacollection.datasets.push(newPerimeter)
            }
        }
    }
</script>