<template>
    <risk-template :risk="risk"
                   :readonly="false"
                   :validation="validation"
                   @validationForm="checkInput($event)">
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
                validation: null
            }
        },
        computed: {
            ...mapGetters(['riskCategories', 'riskStatuses', 'activeUsers', 'getUserByEmail', 'getRiskById', 'getProfile']),
            isNewRisk() {
                return !this.riskId
            },
            responsibleUsers() {
                let users = []
                Array.from(this.activeUsers).forEach(user =>
                    users.push(user.email)
                )
                return users
            }
        },
        created () {
            this.validation = validation
            if (!this.isNewRisk) {
                this.risk = new Risk(this.getRiskById((Number)(this.riskId)))
                this.risk.responsible = this.getResponsibleNames(this.risk.responsible)
            } else {
                this.risk = new Risk()
                this.risk.responsible = []
                this.risk.responsible.push(this.getProfile.email)
                this.risk.cramms = []
            }
        },
        methods: {
            ...mapActions(['addRiskAction', 'updateRiskAction', 'removeRiskAction', 'addCRAMMAction']),
            checkInput(event) {
                this.validator = event
            },
            getResponsibleNames(people) {
                let users = []
                Array.from(people).forEach(user =>
                    users.push(user.email)
                )
                return users
            },
            save() {
                if (!this.validator.validate()) { return }

                this.risk.responsible = this.risk.responsible.map(x => this.getUserByEmail(x))
                if (this.isNewRisk) {
                    this.risk["id"] = this.id
                    this.addRiskAction(this.risk)
                } else {
                    this.risk["id"] = this.riskId
                    this.updateRiskAction(this.risk)
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