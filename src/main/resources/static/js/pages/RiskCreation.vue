<template>
    <v-container grid-list-md text-xs-center>
        <v-card class="py-2 px-2">
        <v-layout row wrap>
            <v-flex xs12>
                <v-text-field
                        label="Title"
                        v-model="text">
                </v-text-field>
            </v-flex>
            <v-flex xs12>
                <v-textarea
                        label="Description"
                        v-model="description">
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <p class="text-xs-left">Risk Category</p>
                <v-overflow-btn
                        :items="riskCategories"
                        label="Risk Category"
                        v-model="category">
                </v-overflow-btn>
            </v-flex>
            <v-flex xs12>
                <v-textarea
                        label="Causes of"
                        v-model="causes">
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <v-textarea
                        label="Description of the consequences"
                        v-model="consequences">
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <p class="text-xs-left">Responsible people</p>
                <v-select
                        v-model="responsible"
                        :items="responsibleUsers"
                        label="Responsible people"
                        multiple
                ></v-select>
            </v-flex>
            <v-flex xs12>
                <p class="text-xs-left">Risk Status</p>
                <v-overflow-btn
                        :items="riskStatuses"
                        label="Risk Status"
                        v-model="status">
                </v-overflow-btn>
            </v-flex>
            <v-flex xs12>
                <v-btn @click="save">
                    Save
                </v-btn>
            </v-flex>
        </v-layout>
        </v-card>
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'
    import { mapGetters } from 'vuex'

    export default {
        name: 'RiskCreation',
        props: ['riskId'],
        data() {
            return {
                text: '',
                description: '',
                category: null,
                causes: '',
                consequences: '',
                responsible: [],
                status: null
            }
        },
        computed: {
            ...mapGetters(['riskCategories', 'riskStatuses', 'activeUsers', 'getUserByEmail', 'getRiskById']),
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
        mounted () {
            if (!this.isNewRisk) {
                const risk = this.getRiskById(this.riskId)
                this.text = risk.text
                this.description = risk.description
                this.category = this.getCategoryName(risk.category)
                this.causes = risk.causes
                this.consequences = risk.consequences
                this.responsible = this.getResponsibleNames(risk.responsible)
                this.status = risk.status
            }
        },
        watch: {
            riskAttr (newVal, oldVal) {
                this.text = newVal.text
                this.description = newVal.description
                this.category = newVal.category
                this.causes = newVal.causes
                this.consequences = newVal.consequences
                this.responsible = newVal.responsible
                this.status = newVal.status
            }
        },
        methods: {
            ...mapActions(['addRiskAction', 'updateRiskAction']),
            getResponsibleNames(people) {
                let users = []
                Array.from(people).forEach(user =>
                    users.push(user.email)
                )
                return users
            },
            getCategoryName(category) {
                console.log(category)
                return category
            },
            save() {
                const risk = {
                    id: this.id,
                    text: this.text,
                    description: this.description,
                    category: this.category,
                    causes: this.causes,
                    consequences: this.consequences,
                    responsible: this.responsible.map(x => this.getUserByEmail(x)),
                    status: this.status
                };

                if (this.isNewRisk)
                    this.addRiskAction(risk)
                else
                    this.updateRiskAction(risk)

                this.$router.push('/')

                this.text = ''
                this.id = ''
                this.description = ''
                this.category = null
                this.causes = ''
                this.consequences = ''
                this.responsible = []
                this.status = null
            }
        }
    }
</script>

<style scoped>

</style>