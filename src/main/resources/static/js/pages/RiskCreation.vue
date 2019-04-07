<template>
    <risk-template :risk="risk"
                   :readonly="false"
                   :validation="validation"
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
        props: ['riskId'],
        data() {
            return {
                validator: null,
                risk: null,
                validation: null,
                newRisk: null
            }
        },
        computed: {
            ...mapGetters(['riskCategories', 'riskStatuses', 'activeUsers', 'getUserByEmail', 'getRiskById', 'getProfile']),
            isNewRisk() {
                return !this.riskId
            }
        },
        created () {
            this.validation = validation
            if (!this.isNewRisk) {
                this.risk = new Risk(this.getRiskById((Number)(this.riskId)))
            } else {
                this.risk = undefined
            }
        },
        methods: {
            ...mapActions(['addRiskAction', 'updateRiskAction', 'removeRiskAction', 'addCRAMMAction']),
            checkInput(event) {
                this.validator = event
            },
            save() {
                if (!this.validator.validate()) { return }

                this.newRisk.responsible = this.newRisk.responsible.map(x => this.getUserByEmail(x))
                if (this.isNewRisk) {
                    this.newRisk["id"] = this.id
                    this.addRiskAction(this.newRisk)
                } else {
                    this.newRisk["id"] = this.riskId
                    this.updateRiskAction(this.newRisk)
                }

                this.$router.push({ name: 'RisksList' })
            }
        },
        components: {
            RiskTemplate
        }
    }
</script>

<style scoped>

</style>