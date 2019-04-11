<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container v-if="validation" fluid>
        <v-stepper v-model="currentStepper">
            <v-stepper-header>
                <template v-for="stage in stages">
                    <v-stepper-step
                            :complete="false"
                            :key="stage.key"
                            :step="stage.key"
                            :editable="true">
                        {{ stage.value }}
                    </v-stepper-step>
                </template>
            </v-stepper-header>

            <v-stepper-items>
                <v-stepper-content
                        :key="stages[0].key"
                        :step="stages[0].key">
                    <v-card class="py-2 px-2">
                        <v-form ref="form0"
                                lazy-validation>
                            <v-layout row wrap align-center>
                                <v-flex xs4>
                                    <v-subheader>Risk title</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.text }}</td>
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
                                    <td v-if="readonly">{{ newRisk.description }}</td>
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
                                    <td v-if="readonly">{{ newRisk.category }}</td>
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
                                    <td v-if="readonly">{{ newRisk.causes }}</td>
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
                                    <td v-if="readonly">{{ newRisk.consequences }}</td>
                                    <v-textarea v-else
                                                placeholder="Description of the consequences"
                                                v-model="newRisk.consequences"
                                                :rules="validation.consequences"
                                                required>
                                    </v-textarea>
                                </v-flex>
                                <v-flex xs12 v-if="readonly">
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4 v-if="readonly">
                                    <v-subheader>Risk status</v-subheader>
                                </v-flex>
                                <v-flex xs8 v-if="readonly">
                                    <td>{{ newRisk.status }}</td>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <slot name="footer-buttons">
                                </slot>
                            </v-layout>
                        </v-form>
                    </v-card>
                </v-stepper-content>
                <v-stepper-content
                        :key="stages[1].key"
                        :step="stages[1].key">
                    <v-card class="py-2 px-2">
                        <v-form ref="form1"
                                lazy-validation v-if="!readonly || isEvaluationStage">
                            <v-layout row wrap align-center>
                                <v-flex xs4>
                                    <v-subheader>Probability</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.probability }}</td>
                                    <v-text-field v-else
                                                  placeholder="Probability"
                                                  v-model="newRisk.probability"
                                                  @input="updateRiskLevel"
                                                  :rules="isEvaluationStage ? validation.probability : []"
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
                                    <td v-if="readonly">{{ newRisk.impact }}</td>
                                    <v-text-field v-else
                                                  placeholder="Impact"
                                                  v-model="newRisk.impact"
                                                  @input="updateRiskLevel"
                                                  :rules="isEvaluationStage ? validation.impact: []"
                                                  required>
                                    </v-text-field>
                                </v-flex>
                                <v-flex xs12 v-if="readonly">
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4 v-if="readonly">
                                    <v-subheader>Risk rate</v-subheader>
                                </v-flex>
                                <v-flex xs8 v-if="readonly">
                                    <td>{{ newRisk.riskRate }}</td>
                                </v-flex>
                                <v-flex xs12 v-if="readonly">
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4 v-if="readonly">
                                    <v-subheader>Risk level</v-subheader>
                                </v-flex>
                                <v-flex xs8 v-if="readonly">
                                    <td>{{ newRisk.riskLevel }}</td>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <slot name="footer-buttons">
                                </slot>
                            </v-layout>
                        </v-form>
                        <v-card-text v-else>
                            <span>No info provided yet.</span>
                        </v-card-text>
                    </v-card>
                </v-stepper-content>
                <v-stepper-content
                        :key="stages[2].key"
                        :step="stages[2].key">
                    <v-card class="py-2 px-2">
                        <v-form ref="form2"
                                lazy-validation v-if="!readonly || isPlanningStage">
                            <v-layout row wrap align-center>
                                <v-flex xs4>
                                    <v-subheader>Responsible people</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.responsible }}</td>
                                    <v-select v-else
                                              chips
                                              v-model="newRisk.responsible"
                                              :items="responsibleUsers"
                                              placeholder="Responsible people"
                                              multiple
                                              :rules="isPlanningStage ? validation.responsible : []"
                                              required
                                    ></v-select>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>Action strategy</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.strategy }}</td>
                                    <v-select v-else
                                              :items="actionStrategies"
                                              placeholder="Risk Strategy"
                                              v-model="newRisk.strategy"
                                              :rules="isPlanningStage ? validation.strategy : []"
                                              required>
                                        ></v-select>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>Start date</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.actionStartDate }}</td>
                                    <slot v-else>
                                    <v-menu :close-on-content-click="true"
                                            :nudge-right="40"
                                            lazy
                                            transition="scale-transition"
                                            offset-y
                                            full-width
                                            min-width="290px">
                                        <template v-slot:activator="{ on }">
                                            <v-text-field
                                                    v-model="newRisk.actionStartDate"
                                                    placeholder="Start date"
                                                    prepend-icon="event"
                                                    :rules="isPlanningStage ? validation.actionStartDate : []"
                                                    required
                                                    readonly
                                                    v-on="on">
                                            </v-text-field>
                                        </template>
                                        <v-date-picker
                                                v-model="newRisk.actionStartDate"
                                                min="1950-01-01"
                                        ></v-date-picker>
                                    </v-menu>
                                    </slot>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>End date</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.actionEndDate }}</td>
                                    <slot v-else>
                                        <v-menu :close-on-content-click="true"
                                                :nudge-right="40"
                                                lazy
                                                transition="scale-transition"
                                                offset-y
                                                full-width
                                                min-width="290px">
                                            <template v-slot:activator="{ on }">
                                                <v-text-field
                                                        v-model="newRisk.actionEndDate"
                                                        placeholder="End date"
                                                        prepend-icon="event"
                                                        :rules="isPlanningStage ? validation.actionEndDate : []"
                                                        required
                                                        readonly
                                                        v-on="on">
                                                </v-text-field>
                                            </template>
                                            <v-date-picker
                                                    v-model="newRisk.actionEndDate"
                                                    min="1950-01-01"
                                            ></v-date-picker>
                                        </v-menu>
                                    </slot>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>Additional info</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.strategyInfo || 'No info provided' }}</td>
                                    <v-textarea v-else
                                                  placeholder="Additional info"
                                                  v-model="newRisk.strategyInfo"
                                                  :rules="isPlanningStage ? validation.strategyInfo : []"
                                                  required>
                                    </v-textarea>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <slot name="footer-buttons">
                                </slot>
                            </v-layout>
                        </v-form>
                        <v-card-text v-else>
                            <span>No info provided yet.</span>
                        </v-card-text>
                    </v-card>
                </v-stepper-content>
                <v-stepper-content
                        :key="stages[3].key"
                        :step="stages[3].key">
                    <v-card class="py-2 px-2">
                        <v-form ref="form3"
                                lazy-validation v-if="!readonly || isMonitoringStage">
                            <v-layout row wrap align-center>
                                <v-flex xs4>
                                    <v-subheader>Planned action start date</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td>{{ newRisk.actionEndDate }}</td>
                                </v-flex>
                                <slot v-if="newRisk.actualActionEndDate || !readonly">
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>Actual end date</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.actualActionEndDate }}</td>
                                    <slot v-else>
                                        <v-menu :close-on-content-click="true"
                                                :nudge-right="40"
                                                lazy
                                                transition="scale-transition"
                                                offset-y
                                                full-width
                                                min-width="290px">
                                            <template v-slot:activator="{ on }">
                                                <v-text-field
                                                        v-model="newRisk.actualActionEndDate"
                                                        placeholder="End date"
                                                        prepend-icon="event"
                                                        :rules="isMonitoringStage ? validation.actualActionEndDate : []"
                                                        required
                                                        readonly
                                                        v-on="on">
                                                </v-text-field>
                                            </template>
                                            <v-date-picker
                                                    v-model="newRisk.actualActionEndDate"
                                                    min="1950-01-01"
                                            ></v-date-picker>
                                        </v-menu>
                                    </slot>
                                </v-flex>
                                </slot>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <!--<v-flex xs12>-->
                                    <!--<v-card-text>-->
                                    <!--<span class="headline font-weight-light">Risk monitoring log</span>-->
                                    <!--</v-card-text>-->
                                <!--</v-flex>-->
                                <!--<v-flex xs12>-->
                                    <!--<v-divider></v-divider>-->
                                <!--</v-flex>-->
                                <slot name="footer-buttons">
                                </slot>
                            </v-layout>
                        </v-form>
                        <v-card-text v-else>
                            <span>No info provided yet.</span>
                        </v-card-text>
                    </v-card>
                </v-stepper-content>
            </v-stepper-items>
        </v-stepper>
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
                currentStepper: 1,
                steps: 2,
                crammStagesAdded: false,
                newRisk: null,
                isInfoProvided: false,
                stages: [
                    { key: 1, value: 'Идентификация', dbValue: 'IDENTIFICATION' },
                    { key: 2, value: 'Оценка', dbValue: 'EVALUATION' },
                    { key: 3, value: 'Планирование' , dbValue: 'PLANNING' },
                    { key: 4, value: 'Мониторинг', dbValue: 'MONITORING' }
                ]
            }
        },
        created () {
//            console.log(this.risk)
            if (!this.readonly) {
                if (this.risk) {
                    this.newRisk = new Risk(this.risk)
                    this.newRisk.responsible = this.getResponsibleNames(this.risk.responsible)
                    this.currentStepper = Risk.convertStageToStep(this.risk.stage)
                } else {
                    this.newRisk = new Risk()
                    this.newRisk.responsible = []
                    this.newRisk.responsible.push(this.getProfile.email)
                }
            } else {
                this.newRisk = new Risk(this.risk)
                this.newRisk.responsible = this.getResponsibleNames(this.risk.responsible)
                this.currentStepper = Risk.convertStageToStep(this.risk.stage)
            }
//            console.log(this.currentStepper)
        },
        mounted () {
            this.$emit('newRisk', this.newRisk)
            this.$emit('validationForm', this.$refs)
        },
        watch: {
            currentStepper (val) {
                console.log(this.currentStepper)
                if (!this.readonly && this.newRisk && this.currentStepper > 0) {
                    this.newRisk.stage = this.stages.find(stage => stage.key === val).value
//                    console.log(this.newRisk.stage)
                }
            }
        },
        computed: {
            ...mapGetters(['riskCategories', 'riskStatuses', 'actionStrategies', 'activeUsers', 'getUserByEmail', 'getRiskById', 'getProfile', 'getProjectById']),
            responsibleUsers() {
                let users = []
                Array.from(this.activeUsers).forEach(user =>
                    users.push(user.email)
                )
                return users
            },
            isIdentificationStage() {
                let risk = this.newRisk
                return risk.text || risk.description || risk.causes || risk.consequences || risk.category
            },
            isEvaluationStage() {
                if (!this.isIdentificationStage) { return false; }
                let risk = this.newRisk
                return risk.probability || risk.impact || risk.riskRate || risk.riskLevel
            },
            isPlanningStage() {
                if (!this.isEvaluationStage) { return false; }
                let risk = this.newRisk
                return risk.responsible || risk.strategy || risk.actionStartDate || risk.actionEndDate || risk.strategyInfo
            },
            isMonitoringStage() {
                if (!this.isPlanningStage) { return false; }
                let risk = this.newRisk
                return !!risk.actualActionEndDate
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