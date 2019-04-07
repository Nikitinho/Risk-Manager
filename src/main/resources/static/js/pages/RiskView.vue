<template>
    <risk-template :risk="risk"
                   :readonly="true"
                   :validation="validation">
        <v-flex xs12 text-xs-right slot="header-buttons">
            <v-btn color="warning"
                   @click="() => this.saveXML(this.risk)">
                Downolad XML
            </v-btn>
            <v-btn color="warning"
                   @click="() => this.savePDF(this.risk)">
                Downolad PDF
            </v-btn>
            <v-btn color="primary"
                   @click="() => this.edit(this.risk)">
                Edit
            </v-btn>
            <v-btn color="error"
                   @click="() => this.del(this.risk)">
                Delete
            </v-btn>
        </v-flex>
        <v-flex xs12 slot="header-buttons">
            <v-divider></v-divider>
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
            ...mapGetters(['riskCategories', 'riskStatuses', 'activeUsers', 'getUserByEmail', 'getRiskById', 'getProfile']),
            responsibleUsers() {
                let users = []
                Array.from(this.activeUsers).forEach(user =>
                    users.push(user.email)
                )
                return users
            }
        },
        created () {
            this.readOnly = (this.readonly === 'true' || this.readonly === true)
            this.validation = validation
            this.risk = new Risk(this.getRiskById((Number)(this.riskId)))
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
            del (risk) {
                this.removeRiskAction(risk)
                this.$router.push({ name: 'ProjectView', params: { projectId: this.projectId } })
            }
        },
        components: {
            RiskTemplate
        }
    }
</script>

<style scoped>

</style>