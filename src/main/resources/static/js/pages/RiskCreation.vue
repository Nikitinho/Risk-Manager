<template>
    <v-container v-if="validation" grid-list-md text-xs-center>
        <v-card class="py-2 px-2">
            <v-form ref="form"
                    v-model="valid"
                    lazy-validation>
        <v-layout row wrap align-center>
            <v-flex xs12 text-xs-right v-if="readOnly">
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
            <v-flex xs12 v-if="readOnly">
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs4>
                <v-subheader>Risk title</v-subheader>
            </v-flex>
            <v-flex xs8>
                <td v-if="readOnly">{{ risk.text }}</td>
                <v-text-field v-else
                        placeholder="Title"
                        v-model="risk.text"
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
                <td v-if="readOnly">{{ risk.description }}</td>
                <v-textarea v-else
                        placeholder="Description"
                        v-model="risk.description"
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
                <td v-if="readOnly">{{ risk.category }}</td>
                <v-select v-else
                        :items="riskCategories"
                        placeholder="Risk Category"
                        v-model="risk.category"
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
                <td v-if="readOnly">{{ risk.causes }}</td>
                <v-textarea v-else
                        placeholder="Causes of"
                        v-model="risk.causes"
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
                <td v-if="readOnly">{{ risk.consequences }}</td>
                <v-textarea v-else
                        placeholder="Description of the consequences"
                        v-model="risk.consequences"
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
                <td v-if="readOnly">{{ risk.responsible }}</td>
                <v-select v-else
                        chips
                        v-model="risk.responsible"
                        :items="responsibleUsers"
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
                <v-subheader>Risk status</v-subheader>
            </v-flex>
            <v-flex xs8>
                <td v-if="readOnly">{{ risk.status }}</td>
                <v-select v-else
                        :items="riskStatuses"
                        placeholder="Risk Status"
                        v-model="risk.status"
                        :rules="validation.status"
                        required>
                    ></v-select>
            </v-flex>
            <v-flex xs12 v-if="!readOnly">
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs12 v-if="!readOnly">
                <v-btn color="success"
                       @click="save">
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
    import printingRiskMixin from 'mixin/PrintingRiskMixin'
    import Risk from 'domain/Risk'

    export default {
        name: 'RiskCreation',
        props: ['riskId', 'readonly'],
        mixins: [printingRiskMixin],
        data() {
            return {
                valid: true,
                risk: null,
                readOnly: false,
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
            this.readOnly = (this.readonly === 'true' || this.readonly === true)
            this.validation = validation
            if (!this.isNewRisk) {
                this.risk = new Risk(this.getRiskById((Number)(this.riskId)))
                this.risk.responsible = this.getResponsibleNames(this.risk.responsible)
            } else {
                this.risk = new Risk()
                this.risk.responsible = []
                this.risk.responsible.push(this.getProfile.email)
            }
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
                this.$router.go()
                this.$router.push({ name: 'RiskCreation', params: { riskId: risk.id, readonly: false } })
            },
            del (risk) {
                this.removeRiskAction(risk)
                this.$router.push('/')
            },
            save() {
                if (!this.$refs.form.validate()) { return }

                this.risk.responsible = this.risk.responsible.map(x => this.getUserByEmail(x))
                if (this.isNewRisk) {
                    this.risk["id"] = this.id
                    this.addRiskAction(this.risk)
                } else {
                    this.risk["id"] = this.riskId
                    this.updateRiskAction(this.risk)
                }

                this.$router.push('/')
            }
        }
    }
</script>

<style scoped>

</style>