<script>
    // Useful sample https://codesandbox.io/s/010k8q4wkn
    //Importing Bubble class from the vue-chartjs wrapper
    import { Doughnut } from 'vue-chartjs'
    import Risk from 'domain/Risk'
    //Exporting this so it can be used in other components
    export default {
        extends: Doughnut,
        props: ['data', 'highRiskValue'],
        data () {
            return {
                datacollection: {
                    //Data to be represented on x-axis
                    labels: [
                        this.$t('project.graph.doughnutChart.low'),
                        this.$t('project.graph.doughnutChart.medium'),
                        this.$t('project.graph.doughnutChart.high')
                    ],
                    datasets: [
                        {
                            backgroundColor: ['green', 'yellow', 'red'],
                            data: []
                        }
                    ]
                },
                highCount: 0,
                mediumCount: 0,
                lowCount: 0
            }
        },
        created () {
            this.addData(this.data)
            this.addDoughnuts()
        },
        watch: {
            highRiskValue: {
                // the callback will be called immediately after the start of the observation
                immediate: true,
                handler (val) {
                    if (!this.$data._chart)  { return }
                    this.$data._chart.destroy()
                    while (this.datacollection.datasets[0].data.length > 0) {
                        this.datacollection.datasets[0].data.pop()
                    }
                    this.highCount = 0
                    this.mediumCount = 0
                    this.lowCount = 0
                    this.addData(this.data)
                    this.addDoughnuts()
                    this.renderChart(this.datacollection, this.options)
                }
            },
            '$i18n.locale': {
                immediate: true,
                handler (val) {
                    if (!this.$data._chart)  { return }
                    this.$data._chart.destroy()
                    this.datacollection.labels[0] =
                        this.$t('project.graph.doughnutChart.low')
                    this.datacollection.labels[1] =
                        this.$t('project.graph.doughnutChart.medium')
                    this.datacollection.labels[2] =
                        this.$t('project.graph.doughnutChart.high')
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
                    this.calculateDoughnuts(element)
                }
            },
            calculateDoughnuts(element) {
                let color = Risk.convertRiskRateToColorCustomizable(element.riskRate, this.highRiskValue || 0.68)
                if (color === 'green') {
                    this.lowCount += 1
                } else if (color === 'yellow') {
                    this.mediumCount += 1
                } else if (color === 'red') {
                    this.highCount += 1
                }
            },
            addDoughnuts() {
                this.datacollection.datasets[0].data.push(this.lowCount)
                this.datacollection.datasets[0].data.push(this.mediumCount)
                this.datacollection.datasets[0].data.push(this.highCount)
            }
        }
    }
</script>