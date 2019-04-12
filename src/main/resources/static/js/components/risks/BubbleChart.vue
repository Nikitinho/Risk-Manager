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
                                min: 0,
                                max: 1,
                                stepSize: 0.1
                            },
                            gridLines: {
                                display: true
                            },
                            scaleLabel: {
                                display: true,
                                labelString: 'Impact'
                            }
                        }],
                        xAxes: [ {
                            ticks: {
                                min: 0,
                                max: 1,
                                stepSize: 0.1
                            },
                            gridLines: {
                                display: false
                            },
                            scaleLabel: {
                                display: true,
                                labelString: 'Probability'
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
                        display: false
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
            }
        }
    }
</script>