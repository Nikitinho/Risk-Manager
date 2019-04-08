<template>
    <risk-template :risk="risk"
                   :readonly="false"
                   :validation="validation"
                   :projectId="projectId"
                   @validationForm="checkInput($event)"
                   @newRisk="newRisk = $event">
        <v-flex xs12 slot="footer-buttons">
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
    import validation from 'validation/RiskFormValidation'
    import Risk from 'domain/Risk'

    export default {
        name: 'RiskCreation',
        props: ['riskId', 'projectId'],
        data() {
            return {
                validator: null,
                risk: null,
                validation: null,
                newRisk: null
            }
        },
        computed: {
            ...mapGetters(['riskCategories', 'riskStatuses', 'activeUsers', 'getUserByEmail', 'getProjectById', 'getProfile']),
            isNewRisk() {
                return !this.riskId
            }
        },
        created () {
            this.validation = validation
            if (!this.isNewRisk) {
                this.risk = this.getProjectById((Number)(this.projectId)).risks.find(x => x.id === this.riskId)
            } else {
                this.risk = undefined
            }
        },
        methods: {
            ...mapActions(['addRiskAction', 'updateRiskAction', 'removeRiskAction']),
            checkInput(event) {
                this.validator = event
            },
            save() {
                if (!this.validator.validate()) { return }

                this.newRisk.responsible = this.newRisk.responsible.map(x => this.getUserByEmail(x))
                this.newRisk.project = {
                    id: this.projectId
                }
                if (this.isNewRisk) {
                    this.newRisk["id"] = this.id
                    this.addRiskAction(this.newRisk)
                } else {
                    this.newRisk["id"] = this.riskId
                    this.updateRiskAction(this.newRisk)
                }

                this.$router.push({ name: 'ProjectView', props: { projectId: this.projectId } })
            }
        },
        components: {
            RiskTemplate
        }
    }
</script>

<style scoped>

</style>