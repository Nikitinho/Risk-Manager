<script>
    // Useful site with samples: www.sitepoint.com/creating-beautiful-charts-vue-chart-js
    //Importing Bubble class from the vue-chartjs wrapper
    import { Bubble } from 'vue-chartjs'
    import Risk from 'domain/Risk'
    //Exporting this so it can be used in other components
    export default {
        extends: Bubble,
        props: ['data', 'highRiskValue'],
        data () {
            return {
                datacollection: {
                    //Data to be represented on x-axis
                    label: 'Threats',
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
                                labelString: this.$t('project.graph.bubbleChart.impact')
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
                                labelString: this.$t('project.graph.bubbleChart.probability')
                            }
                        }]
                    },
                    tooltips: {
                        callbacks: {
                            label(tooltipItem, data) {
                                const label = data.datasets[tooltipItem.datasetIndex].label
                                const value = parseFloat(Number(tooltipItem.yLabel) * Number(tooltipItem.xLabel)).toFixed(2)

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
        watch: {
            highRiskValue: {
                // the callback will be called immediately after the start of the observation
                immediate: true,
                handler (val) {
                    if (!this.$data._chart)  { return }
                    this.$data._chart.destroy()
                    while (this.datacollection.datasets.length > 0) {
                        this.datacollection.datasets.pop()
                    }
                    this.addData(this.data)
                    this.renderChart(this.datacollection, this.options)
                }
            },
            '$i18n.locale': {
                immediate: true,
                handler (val) {
                    if (!this.$data._chart)  { return }
                    this.$data._chart.destroy()
                    this.options.scales.yAxes[0].scaleLabel.labelString =
                        this.$t('project.graph.bubbleChart.impact')
                    this.options.scales.xAxes[0].scaleLabel.labelString =
                        this.$t('project.graph.bubbleChart.probability')
                    this.renderChart(this.datacollection, this.options)
                }
            }
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
                this.drawCurvedLine(this.highRiskValue || 0.68)
            },
            addNewBubble(element) {
                let newBubble = {
                    label: element.text,
                    backgroundColor: Risk.convertRiskRateToColorCustomizable(element.riskRate, this.highRiskValue || 0.68),
                    pointBackgroundColor: 'white',
                    borderWidth: 2,
                    pointBorderColor: '#000000',
                    data: [
                        {
                            x: element.probability,
                            y: element.impact,
                            r: 10
                        }
                    ]
                }
                this.datacollection.datasets.push(newBubble)
            },
            drawCurvedLine(highRiskValue) {
                let curvedLine = {
                    data: [
                    ],
                    label: 'Curved line',
                    borderColor: 'black',
                    borderWidth: 1,
                    pointBackgroundColor: '#000',
                    pointBorderColor: '#000',
                    pointRadius: 0,
                    pointHoverRadius: 0,
                    fill: false,
                    showLine: true,
                    type: 'line',
                    cubicInterpolationMode: 'monotone'
                }
                for(let i = 0.0; i <= 1.0; i += 0.01) {
                    let newDot = {
                        x: i,
                        y: highRiskValue/i
                    }
                    curvedLine.data.push(newDot)
                }
                this.datacollection.datasets.push(curvedLine)
            }

        }
    }
</script>