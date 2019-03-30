<template>
    <v-container grid-list-md text-xs-center>
        <v-card class="py-2 px-2">
        <v-layout row wrap>
            <v-flex xs12>
                <v-text-field
                        label="Title"
                        :disabled="readonly"
                        v-model="text">
                </v-text-field>
            </v-flex>
            <v-flex xs12>
                <v-textarea
                        label="Description"
                        :disabled="readonly"
                        v-model="description">
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <p class="text-xs-left">Risk Category</p>
                <v-overflow-btn
                        :items="riskCategories"
                        :disabled="readonly"
                        label="Risk Category"
                        v-model="category">
                </v-overflow-btn>
            </v-flex>
            <v-flex xs12>
                <v-textarea
                        label="Causes of"
                        :disabled="readonly"
                        v-model="causes">
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <v-textarea
                        label="Description of the consequences"
                        :disabled="readonly"
                        v-model="consequences">
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <p class="text-xs-left">Responsible people</p>
                <v-select
                        v-model="responsible"
                        :items="responsibleUsers"
                        :disabled="readonly"
                        label="Responsible people"
                        multiple
                ></v-select>
            </v-flex>
            <v-flex xs12>
                <p class="text-xs-left">Risk Status</p>
                <v-overflow-btn
                        :items="riskStatuses"
                        :disabled="readonly"
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
        props: ['riskId', 'readonly'],
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
        mounted () {
            if (!this.isNewRisk) {
                const risk = this.getRiskById(this.riskId)
                this.text = risk.text
                this.description = risk.description
                this.category = risk.category
                this.causes = risk.causes
                this.consequences = risk.consequences
                this.responsible = this.getResponsibleNames(risk.responsible)
                this.status = risk.status
            } else {
                this.responsible.push(this.getProfile.email)
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
            save() {
                const risk = {
                    text: this.text,
                    description: this.description,
                    category: this.category,
                    causes: this.causes,
                    consequences: this.consequences,
                    responsible: this.responsible.map(x => this.getUserByEmail(x)),
                    status: this.status
                };
                if (this.isNewRisk) {
                    risk["id"] = this.id
                    this.addRiskAction(risk)
                } else {
                    risk["id"] = this.riskId
                    this.updateRiskAction(risk)
                }

                this.$router.push('/')
            }
        }
    }
</script>

<style scoped>

</style>