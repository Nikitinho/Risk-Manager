<template>
    <risk-template :risk="risk"
                   :readonly="false"
                   :validation="validation"
                   :projectId="projectId"
                   @validationForm="checkInput($event)"
                   @newRisk="newRisk = $event">
        <v-flex xs12 slot="footer-buttons" text-xs-center>
            <v-btn color="success"
                   @click="save">
                Save
            </v-btn>
        </v-flex>
    </risk-template>
</template>

<script>
    import { mapActions } from 'vuex'
    import { mapGetters } from 'vuex'
    import RiskTemplate from 'pages/RiskTemplate.vue'
    import Risk from 'domain/Risk'

    export default {
        name: 'RiskCreation',
        props: ['riskId', 'projectId'],
        data() {
            return {
                validator: null,
                validation: null,
                risk: null,
                newRisk: null
            }
        },
        computed: {
            ...mapGetters(['riskCategories', 'riskStatuses', 'activeUsers', 'getUserByEmail', 'getProjectById', 'getProfile']),
            isNewRisk() {
                return !this.riskId
            }
        },
        async created () {
            this.validation = require(`validation/${this.$i18n.locale}/RiskFormValidation`).default
            if (!this.isNewRisk) {
                this.risk = this.getProjectById((Number)(this.projectId)).risks.find(x => x.id === this.riskId)
            } else {
                this.risk = undefined
            }
            console.log(this.risk)
        },
        methods: {
            ...mapActions(['addRiskAction', 'updateRiskAction', 'removeRiskAction']),
            checkInput(event) {
                this.validator = event
            },
            async save() {
                if (!Object.keys(this.validator).every(i => this.validator[i].validate())) { return; }

                this.newRisk.responsible = this.newRisk.responsible.map(x => this.getUserByEmail(x))
                this.updateRiskLevel()
                this.newRisk.project = {
                    id: this.projectId
                }
                if (this.isNewRisk) {
                    this.newRisk["id"] = this.id
                    await this.addRiskAction(this.newRisk)
                } else {
                    this.newRisk["id"] = this.riskId
                    await this.updateRiskAction(this.newRisk)
                }

                this.$router.push({ name: 'ProjectView', props: { projectId: this.projectId } })
            },
            updateRiskLevel() {
                if (!this.newRisk.probability || !this.newRisk.impact) {
                    return
                }
                this.newRisk.probability = parseFloat(this.newRisk.probability).toFixed(1)
                this.newRisk.impact = parseFloat(this.newRisk.impact).toFixed(1)
                let riskRate = this.newRisk.probability * this.newRisk.impact
                this.newRisk.riskRate = parseFloat(riskRate).toFixed(2)
                this.newRisk.riskLevel = Risk.convertRiskRateToLevel(this.newRisk.riskRate)
            }
        },
        components: {
            RiskTemplate
        }
    }
</script>

<style scoped>

</style>