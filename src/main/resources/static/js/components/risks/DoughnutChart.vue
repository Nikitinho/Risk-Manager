<script>
    // Useful sample https://codesandbox.io/s/010k8q4wkn
    //Importing Bubble class from the vue-chartjs wrapper
    import { Doughnut } from 'vue-chartjs'
    import Risk from 'domain/Risk'
    //Exporting this so it can be used in other components
    export default {
        extends: Doughnut,
        props: ['data'],
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
                let level = element.riskRate
                if (level >= 0.0 && level < 0.34) {
                    this.lowCount += 1
                } else if (level >= 0.34 && level < 0.68) {
                    this.mediumCount += 1
                } else if (level >= 0.68 && level <= 1.0) {
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