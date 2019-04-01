<template>
    <v-container v-if="validation" grid-list-md text-xs-center>
        <v-card class="py-2 px-2">
            <v-form ref="form"
                    v-model="valid"
                    lazy-validation>
        <v-layout row wrap>
            <v-flex xs4>
                <v-subheader>Risk title</v-subheader>
            </v-flex>
            <v-flex xs8>
                <v-text-field
                        placeholder="Title"
                        :disabled="readonly"
                        v-model="text"
                        :rules="validation.title"
                        required>
                </v-text-field>
            </v-flex>
            <v-flex xs12>
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs4>
                <v-subheader>Risk description</v-subheader>
            </v-flex>
            <v-flex xs8>
                <v-textarea
                        placeholder="Description"
                        :disabled="readonly"
                        v-model="description"
                        :rules="validation.description"
                        required>
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs4>
                <v-subheader>Risk category</v-subheader>
            </v-flex>
            <v-flex xs8>
                <v-select
                        :items="riskCategories"
                        :disabled="readonly"
                        placeholder="Risk Category"
                        v-model="category"
                        :rules="validation.category"
                        required>
                ></v-select>
            </v-flex>
            <v-flex xs12>
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs4>
                <v-subheader>Risk causes</v-subheader>
            </v-flex>
            <v-flex xs8>
                <v-textarea
                        placeholder="Causes of"
                        :disabled="readonly"
                        v-model="causes"
                        :rules="validation.causes"
                        required>
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs4>
                <v-subheader>Description of the consequences</v-subheader>
            </v-flex>
            <v-flex xs8>
                <v-textarea
                        placeholder="Description of the consequences"
                        :disabled="readonly"
                        v-model="consequences"
                        :rules="validation.consequences"
                        required>
                </v-textarea>
            </v-flex>
            <v-flex xs12>
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs4>
                <v-subheader>Responsible people</v-subheader>
            </v-flex>
            <v-flex xs8>
                <v-select
                        chips
                        v-model="responsible"
                        :items="responsibleUsers"
                        :disabled="readonly"
                        placeholder="Responsible people"
                        multiple
                        :rules="validation.responsible"
                        required
                ></v-select>
            </v-flex>
            <v-flex xs12>
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs4>
                <v-subheader>Responsible people</v-subheader>
            </v-flex>
            <v-flex xs8>
                <v-select
                        :items="riskStatuses"
                        :disabled="readonly"
                        placeholder="Risk Status"
                        v-model="status"
                        :rules="validation.status"
                        required>
                    ></v-select>
            </v-flex>
            <v-flex xs12>
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs12>
                <v-btn @click="save">
                    Save
                </v-btn>
            </v-flex>
        </v-layout>
            </v-form>
        </v-card>
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'
    import { mapGetters } from 'vuex'
    import validation from 'validation/RiskFormValidation'

    export default {
        name: 'RiskCreation',
        props: ['riskId', 'readonly'],
        data() {
            return {
                valid: true,
                text: '',
                description: '',
                category: null,
                causes: '',
                consequences: '',
                responsible: [],
                status: null,
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
        mounted () {
            this.validation = validation
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
                if (!this.$refs.form.validate())
                    return

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