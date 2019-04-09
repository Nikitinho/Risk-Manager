<template>
    <v-container v-if="validation" fluid>
        <v-card class="py-2 px-2">
            <v-form ref="form"
                    lazy-validation>
        <v-layout row wrap align-center>
            <slot name="header-buttons">
            </slot>
            <v-flex xs4>
                <v-subheader>Risk title</v-subheader>
            </v-flex>
            <v-flex xs8>
                <td v-if="readonly">{{ risk.text }}</td>
                <v-text-field v-else
                        placeholder="Title"
                        v-model="newRisk.text"
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
                <td v-if="readonly">{{ risk.description }}</td>
                <v-textarea v-else
                        placeholder="Description"
                        v-model="newRisk.description"
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
                <td v-if="readonly">{{ risk.category }}</td>
                <v-select v-else
                        :items="riskCategories"
                        placeholder="Risk Category"
                        v-model="newRisk.category"
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
                <td v-if="readonly">{{ risk.causes }}</td>
                <v-textarea v-else
                        placeholder="Causes of"
                        v-model="newRisk.causes"
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
                <td v-if="readonly">{{ risk.consequences }}</td>
                <v-textarea v-else
                        placeholder="Description of the consequences"
                        v-model="newRisk.consequences"
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
                <td v-if="readonly">{{ risk.responsible }}</td>
                <v-select v-else
                        chips
                        v-model="newRisk.responsible"
                        :items="responsibleUsers"
                        placeholder="Responsible people"
                        multiple
                        :rules="validation.responsible"
                        required
                ></v-select>
            </v-flex>
            <v-flex xs12 v-if="readonly">
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs4 v-if="readonly">
                <v-subheader>Risk status</v-subheader>
            </v-flex>
            <v-flex xs8 v-if="readonly">
                <td>{{ risk.status }}</td>
            </v-flex>
            <v-flex xs12>
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs4>
                <v-subheader>Probability</v-subheader>
            </v-flex>
            <v-flex xs8>
                <td v-if="readonly">{{ risk.probability }}</td>
                <v-text-field v-else
                              placeholder="Probability"
                              v-model="newRisk.probability"
                              :rules="validation.probability"
                              required>
                </v-text-field>
            </v-flex>
            <v-flex xs12>
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs4>
                <v-subheader>Impact</v-subheader>
            </v-flex>
            <v-flex xs8>
                <td v-if="readonly">{{ risk.impact }}</td>
                <v-text-field v-else
                              placeholder="Impact"
                              v-model="newRisk.impact"
                              :rules="validation.impact"
                              required>
                </v-text-field>
            </v-flex>
            <v-flex xs12 v-if="!readonly">
                <v-divider></v-divider>
            </v-flex>
            <slot name="footer-buttons">
            </slot>
        </v-layout>
            </v-form>
        </v-card>
        <risk-comments v-if="readonly" :comments="risk.comments" :riskId="risk.id"></risk-comments>
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'
    import { mapGetters } from 'vuex'
    import printingRiskMixin from 'mixin/PrintingRiskMixin'
    import BubbleChart from 'components/risks/BubbleChart.vue'
    import Risk from 'domain/Risk'
    import RiskComments from 'components/risks/RiskComments.vue'

    export default {
        name: 'RiskTemplate',
        props: ['risk', 'readonly', 'validation', 'projectId'],
        mixins: [printingRiskMixin],
        data() {
            return {
                crammStagesAdded: false,
                newRisk: null,
                isInfoProvided: false,
            }
        },
        created () {
            // Don't know why usual "short-circuit" not working
            let isUndefined = this.risk === undefined || this.risk === null
            let isNoCrammsArray = (isUndefined) ? true : this.risk.cramms === undefined
            let isNoCrammsNodes = (isNoCrammsArray) ? true : this.risk.cramms.length === 0
            this.crammStagesAdded = !isUndefined && !isNoCrammsArray && !isNoCrammsNodes
            if (!this.readonly) {
                if (this.risk) {
                    this.newRisk = new Risk(this.risk)
                    this.newRisk.responsible = this.getResponsibleNames(this.risk.responsible)
                } else {
                    this.newRisk = new Risk()
                    this.newRisk.responsible = []
                    this.newRisk.responsible.push(this.getProfile.email)
                }
            }
            console.log(this.risk)
        },
        mounted () {
            this.$emit('newRisk', this.newRisk)
            this.$emit('validationForm', this.$refs.form)
        },
        computed: {
            ...mapGetters(['riskCategories', 'riskStatuses', 'activeUsers', 'getUserByEmail', 'getRiskById', 'getProfile', 'getProjectById']),
            responsibleUsers() {
                let users = []
                Array.from(this.activeUsers).forEach(user =>
                    users.push(user.email)
                )
                return users
            }
        },
        methods: {
            getResponsibleNames(people) {
                let users = []
                Array.from(people).forEach(user =>
                    users.push(user.email)
                )
                return users
            },
            updateRiskLevel() {
                if (!this.newRisk.probability || !this.newRisk.impact) {
                    return
                }
                this.newRisk.riskRate = this.newRisk.probability * this.newRisk.impact
                this.newRisk.riskLevel = Risk.convertRiskRateToLevel(this.newRisk.riskRate)
            }
        },
        components: {
            BubbleChart, RiskComments
        }
    }
</script>

<style scoped>

</style>