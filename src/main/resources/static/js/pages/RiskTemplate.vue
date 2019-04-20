<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container v-if="validation" fluid>
        <v-card class="py-2 px-2 mb-1">
            <v-card-title>
                <span class="headline font-weight-light">{{ newRisk.text || $t('risk.fields.title') }}
                </span>
                <v-spacer></v-spacer>
                <span v-if="!readonly">
                    <v-checkbox v-model="newRisk.areNotificationSent"
                                :label="$t('risk.fields.areNotificationSent')">
                </v-checkbox>
                </span>
            </v-card-title>
        </v-card>
        <v-stepper v-model="currentStepper">
            <v-stepper-header>
                <template v-for="stage in stages">
                    <v-stepper-step
                            :complete="false"
                            :key="stage.key"
                            :step="stage.key"
                            :editable="true">
                        {{$t(`risk.stages.${stage.value.toLowerCase()}`)}}
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
                                    <v-subheader>{{$t('risk.fields.title')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.text }}</td>
                                    <v-text-field v-else
                                                  :placeholder="$t('risk.fields.title')"
                                                  v-model="newRisk.text"
                                                  :rules="validation.title"
                                                  required>
                                    </v-text-field>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>{{$t('risk.fields.description')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.description }}</td>
                                    <v-textarea v-else
                                                :placeholder="$t('risk.fields.description')"
                                                v-model="newRisk.description"
                                                :rules="validation.description"
                                                required>
                                    </v-textarea>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>{{$t('risk.fields.category')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">
                                        {{$t(`risk.categories.${newRisk.category.toLowerCase()}`)}}
                                    </td>
                                    <v-select v-else
                                              :items="riskCategories"
                                              :placeholder="$t('risk.fields.category')"
                                              v-model="newRisk.category"
                                              :rules="validation.category"
                                              required>
                                        <template slot="item" slot-scope="data">
                                            {{$t(`risk.categories.${data.item.toLowerCase()}`)}}
                                        </template>
                                        <template slot="selection" slot-scope="data">
                                            {{$t(`risk.categories.${data.item.toLowerCase()}`)}}
                                        </template>
                                    </v-select>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>{{$t('risk.fields.causes')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.causes }}</td>
                                    <v-textarea v-else
                                                :placeholder="$t('risk.fields.causes')"
                                                v-model="newRisk.causes"
                                                :rules="validation.causes"
                                                required>
                                    </v-textarea>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>{{$t('risk.fields.consequences')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.consequences }}</td>
                                    <v-textarea v-else
                                                :placeholder="$t('risk.fields.consequences')"
                                                v-model="newRisk.consequences"
                                                :rules="validation.consequences"
                                                required>
                                    </v-textarea>
                                </v-flex>
                                <v-flex xs12 v-if="readonly">
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4 v-if="readonly">
                                    <v-subheader>{{$t('risk.fields.status')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8 v-if="readonly">
                                    <td>{{$t(`risk.statuses.${newRisk.status.toLowerCase()}`)}}</td>
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
                                    <v-subheader>{{$t('risk.fields.probability')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.probability }}</td>
                                    <v-text-field v-else
                                                  :placeholder="$t('risk.fields.probability')"
                                                  v-model="newRisk.probability"
                                                  :rules="isEvaluationStage ? validation.probability : []"
                                                  required>
                                    </v-text-field>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>{{$t('risk.fields.impact')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.impact }}</td>
                                    <v-text-field v-else
                                                  :placeholder="$t('risk.fields.impact')"
                                                  v-model="newRisk.impact"
                                                  :rules="isEvaluationStage ? validation.impact: []"
                                                  required>
                                    </v-text-field>
                                </v-flex>
                                <v-flex xs12 v-if="readonly">
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4 v-if="readonly">
                                    <v-subheader>{{$t('risk.fields.riskRate')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8 v-if="readonly">
                                    <td>{{ newRisk.riskRate }}</td>
                                </v-flex>
                                <v-flex xs12 v-if="readonly">
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4 v-if="readonly">
                                    <v-subheader>{{$t('risk.fields.riskLevel')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8 v-if="readonly">
                                    <td>{{$t(`risk.levels.${newRisk.riskLevel.toLowerCase()}`)}}</td>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <slot name="footer-buttons">
                                </slot>
                            </v-layout>
                        </v-form>
                        <v-card-text v-else>
                            <span>{{$t('risk.stages.emptyStage')}}</span>
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
                                    <v-subheader>{{$t('risk.fields.responsible')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.responsible }}</td>
                                    <v-select v-else
                                              chips
                                              v-model="newRisk.responsible"
                                              :items="responsibleUsers"
                                              :placeholder="$t('risk.fields.responsible')"
                                              multiple
                                              :rules="isPlanningStage ? validation.responsible : []"
                                              required
                                    ></v-select>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>{{$t('risk.fields.strategy')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{$t(`risk.strategies.${newRisk.strategy.toLowerCase()}`)}}</td>
                                    <v-select v-else
                                              :items="actionStrategies"
                                              :placeholder="$t('risk.fields.strategy')"
                                              v-model="newRisk.strategy"
                                              :rules="isPlanningStage ? validation.strategy : []"
                                              required>
                                        <template slot="item" slot-scope="data">
                                            {{$t(`risk.strategies.${data.item.toLowerCase()}`)}}
                                        </template>
                                        <template slot="selection" slot-scope="data">
                                            {{$t(`risk.strategies.${data.item.toLowerCase()}`)}}
                                        </template>
                                    </v-select>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <v-flex xs4>
                                    <v-subheader>{{$t('risk.fields.actionStartDate')}}</v-subheader>
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
                                                        :placeholder="$t('risk.fields.actionStartDate')"
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
                                    <v-subheader>{{$t('risk.fields.actionEndDate')}}</v-subheader>
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
                                                        :placeholder="$t('risk.fields.actionEndDate')"
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
                                    <v-subheader>{{$t('risk.fields.strategyInfo')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{ newRisk.strategyInfo }}</td>
                                    <v-textarea v-else
                                                :placeholder="$t('risk.fields.strategyInfo')"
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
                            <span>{{$t('risk.stages.emptyStage')}}</span>
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
                                    <v-subheader>{{$t('risk.fields.actionEndDate')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td>{{ newRisk.actionEndDate }}</td>
                                </v-flex>
                                <slot v-if="newRisk.actualActionEndDate || !readonly">
                                    <v-flex xs12>
                                        <v-divider></v-divider>
                                    </v-flex>
                                    <v-flex xs4>
                                        <v-subheader>{{$t('risk.fields.actualActionEndDate')}}</v-subheader>
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
                                                            :placeholder="$t('risk.fields.actualActionEndDate')"
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
                                <v-flex xs4>
                                    <v-subheader>{{$t('risk.fields.hasWorked')}}</v-subheader>
                                </v-flex>
                                <v-flex xs8>
                                    <td v-if="readonly">{{$t(`constants.${newRisk.hasWorked}`)}}</td>
                                    <v-switch v-else v-model="newRisk.hasWorked" color="success">
                                    </v-switch>
                                </v-flex>
                                <v-flex xs12>
                                    <v-divider></v-divider>
                                </v-flex>
                                <slot name="footer-buttons">
                                </slot>
                            </v-layout>
                        </v-form>
                        <v-card-text v-else>
                            <span>{{$t('risk.stages.emptyStage')}}</span>
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
                    { key: 1, value: 'IDENTIFICATION' },
                    { key: 2, value: 'EVALUATION' },
                    { key: 3, value: 'PLANNING' },
                    { key: 4, value: 'MONITORING' }
                ]
            }
        },
        created () {
            if (!this.readonly) {
                if (this.risk) {
                    this.newRisk = new Risk(this.risk)
                    this.newRisk.responsible = this.getResponsibleNames(this.risk.responsible)
                    this.currentStepper = Risk.convertStageToStep(this.risk.stage)
                } else {
                    this.newRisk = new Risk()
                    this.newRisk.responsible = []
                }
            } else {
                this.newRisk = new Risk(this.risk)
                this.newRisk.responsible = this.getResponsibleNames(this.risk.responsible)
                this.currentStepper = Risk.convertStageToStep(this.risk.stage)
            }
        },
        mounted () {
            this.$emit('newRisk', this.newRisk)
            this.$emit('validationForm', this.$refs)
        },
        watch: {
            currentStepper (val) {
                if (!this.readonly && this.newRisk && this.currentStepper > 0) {
                    this.newRisk.stage = this.stages.find(stage => stage.key === val).value
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
                // risk.responsible temporarily excluded from this list
                return risk.strategy || risk.actionStartDate || risk.actionEndDate || risk.strategyInfo
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
            }
        },
        components: {
            BubbleChart, RiskComments
        }
    }
</script>

<style scoped>

</style>