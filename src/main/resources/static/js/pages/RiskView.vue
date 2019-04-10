<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <risk-template :risk="risk"
                   :readonly="true"
                   :validation="validation">
        <v-flex v-if="!$vuetify.breakpoint.mdAndDown" xs12 text-xs-right slot="footer-buttons">
            <v-layout row align-center justify-end>
                <v-menu
                        bottom
                        origin="center center"
                        transition="scale-transition"
                >
                    <template v-slot:activator="{ on }">
                        <v-btn
                                color="primary"
                                dark
                                v-on="on"
                        >
                            Change status
                        </v-btn>
                    </template>

                    <v-list>
                        <v-list-tile
                                v-for="status in riskStatuses"
                                :key="status"
                                @click="() => changeStatus(status)">
                            <v-list-tile-title>{{ status }}</v-list-tile-title>
                        </v-list-tile>
                    </v-list>
                </v-menu>
                <v-menu
                        bottom
                        origin="center center"
                        transition="scale-transition"
                >
                    <template v-slot:activator="{ on }">
                        <v-btn
                                color="warning"
                                dark
                                v-on="on"
                        >
                            Export document
                        </v-btn>
                    </template>

                    <v-list>
                        <v-list-tile
                                v-for="item in exportOptions"
                                :key="item.type"
                                @click="() => exportDocument(risk, item.type)">
                            <v-list-tile-title>{{item.title}}</v-list-tile-title>
                        </v-list-tile>
                    </v-list>
                </v-menu>
            <v-btn color="primary"
                   @click="() => this.edit(this.risk)">
                Edit
            </v-btn>
            <v-btn color="error"
                   @click="() => this.del(this.risk)">
                Delete
            </v-btn>
            </v-layout>
        </v-flex>
    </risk-template>
</template>

<script>
    import { mapActions } from 'vuex'
    import { mapGetters } from 'vuex'
    import RiskTemplate from 'pages/RiskTemplate.vue'
    import validation from 'validation/RiskFormValidation'
    import printingRiskMixin from 'mixin/PrintingRiskMixin'
    import xmlExportMixin from 'mixin/xmlExportMixin'
    import Risk from 'domain/Risk'

    export default {
        name: 'RiskView',
        props: ['riskId', 'projectId'],
        mixins: [printingRiskMixin, xmlExportMixin],
        data() {
            return {
                risk: null,
                validation: null
            }
        },
        computed: {
            ...mapGetters(['riskCategories', 'riskStatuses', 'activeUsers', 'getUserByEmail', 'getProjectById', 'getProfile']),
            responsibleUsers() {
                let users = []
                Array.from(this.activeUsers).forEach(user =>
                    users.push(user.email)
                )
                return users
            },
            exportOptions() {
                let options = []
                options.push({ title: 'В pdf', type: 'PDF' })
                options.push({ title: 'В xml', type: 'XML' })
                return options
            }
        },
        created () {
            this.validation = validation
            this.risk = new Risk(this.getProjectById((Number)(this.projectId))
                .risks.find(x => (Number)(x.id) === (Number)(this.riskId)))
            this.risk.responsible = this.getResponsibleNames(this.risk.responsible)
        },
        methods: {
            ...mapActions(['addRiskAction', 'updateRiskAction', 'removeRiskAction']),
            getResponsibleNames(people) {
                let users = []
                Array.from(people).forEach(user =>
                    users.push(user.email)
                )
                return users
            },
            edit (risk) {
                this.$router.push({ name: 'RiskEdition', params: { projectId: this.projectId, riskId: risk.id } })
            },
            async del (risk) {
                awthis.removeRiskAction(risk)
                this.$router.push({ name: 'ProjectView', params: { projectId: this.projectId } })
            },
            exportDocument(risk, DocumentType) {
                if (DocumentType === 'PDF') {
                    this.savePDF(risk)
                } else if (DocumentType === 'XML') {
                    this.saveXML(risk)
                }
            },
            async changeStatus(status) {
                let newRisk = new Risk(this.risk)
                newRisk.status = status
                newRisk.responsible = newRisk.responsible.map(x => this.getUserByEmail(x))
                newRisk.project = {
                    id: (Number)(this.projectId)
                }
                newRisk["id"] = this.riskId
                await this.updateRiskAction(newRisk)
                this.$router.go()
            }
        },
        components: {
            RiskTemplate
        }
    }
</script>

<style scoped>

</style>