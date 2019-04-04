<template>
    <v-container v-if="validation" grid-list-md text-xs-center>
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
                <td v-if="readonly">{{ risk.description }}</td>
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
                <td v-if="readonly">{{ risk.category }}</td>
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
                <td v-if="readonly">{{ risk.causes }}</td>
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
                <td v-if="readonly">{{ risk.consequences }}</td>
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
                <td v-if="readonly">{{ risk.responsible }}</td>
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
                <td v-if="readonly">{{ risk.status }}</td>
                <v-select v-else
                        :items="riskStatuses"
                        placeholder="Risk Status"
                        v-model="risk.status"
                        :rules="validation.status"
                        required>
                </v-select>
            </v-flex>
            <v-flex xs12 v-if="!readonly">
                <v-divider></v-divider>
            </v-flex>
            <v-flex xs12 v-if="!crammStagesAdded && !readonly">
                <v-btn color="success"
                       @click="() => { this.crammStagesAdded = true;
                       this.risk.cramms.push({'asset': '', 'assetRate': '', 'threat': '', 'vulnerability': '', 'vulnerabilityRate': ''});
                       }">
                    Add cramm criteria
                </v-btn>
            </v-flex>
            <slot v-if="!readonly" v-for="(item, index) in risk.cramms">
                <v-flex xs4>
                    <v-subheader>
                    <v-layout column>
                        <v-btn fab dark color="indigo" @click="addCRAMMCriteria">
                            <v-icon dark>add</v-icon>
                        </v-btn>
                        <v-btn fab dark color="red" @click="() => removeCRAMMCriteria(index)">
                            <v-icon dark>remove</v-icon>
                        </v-btn>
                    </v-layout>
                    </v-subheader>
                </v-flex>
                <v-flex xs8>
                    <v-layout column>
                        <v-layout row>
                            <v-flex xs9>
                                <td v-if="readonly">{{ risk.cramms[index].asset }}</td>
                                <v-text-field v-else
                                              placeholder="Asset"
                                              :rules="validation.cramms.asset"
                                              v-model="risk.cramms[index].asset">
                                </v-text-field>
                            </v-flex>
                            <v-flex xs3>
                                <td v-if="readonly">{{ risk.cramms[index].assetRate }}</td>
                                <v-text-field v-else
                                              type="number"
                                              placeholder="Asset Rate"
                                              :rules="validation.cramms.assetRate"
                                              v-model="risk.cramms[index].assetRate">
                                </v-text-field>
                            </v-flex>
                        </v-layout>
                        <v-layout row>
                            <v-flex xs12>
                                <td v-if="readonly">{{ risk.cramms[index].threat }}</td>
                                <v-text-field v-else
                                              placeholder="Threat"
                                              :rules="validation.cramms.threat"
                                              v-model="risk.cramms[index].threat">
                                </v-text-field>
                            </v-flex>
                        </v-layout>
                        <v-layout row>
                            <v-flex xs9>
                                <td v-if="readonly">{{ risk.cramms[index].vulnerability }}</td>
                                <v-text-field v-else
                                              placeholder="Vulnerability"
                                              :rules="validation.cramms.vulnerability"
                                              v-model="risk.cramms[index].vulnerability">
                                </v-text-field>
                            </v-flex>
                            <v-flex xs3>
                                <td v-if="readonly">{{ risk.cramms[index].vulnerabilityRate }}</td>
                                <v-text-field v-else
                                              type="number"
                                              placeholder="Vulnerability Rate"
                                              :rules="validation.cramms.vulnerabilityRate"
                                              v-model="risk.cramms[index].vulnerabilityRate">
                                </v-text-field>
                            </v-flex>
                        </v-layout>
                    </v-layout>
                </v-flex>
                <v-flex xs12>
                    <v-divider></v-divider>
                </v-flex>
            </slot>
            <slot v-if="readonly" v-for="(item, index) in risk.cramms">
                <v-flex xs12>
                    <v-divider></v-divider>
                </v-flex>
                <v-flex xs4>
                    <v-subheader>Asset</v-subheader>
                </v-flex>
                <v-flex xs8>
                    <td>{{ risk.cramms[index].asset }} {{ risk.cramms[index].assetRate }}</td>
                </v-flex>
                <v-flex xs4>
                    <v-subheader>Threat</v-subheader>
                </v-flex>
                <v-flex xs8>
                    <td>{{ risk.cramms[index].threat }}</td>
                </v-flex>
                <v-flex xs4>
                    <v-subheader>Vulnerability</v-subheader>
                </v-flex>
                <v-flex xs8>
                    <td>{{ risk.cramms[index].vulnerability }} {{ risk.cramms[index].vulnerabilityRate }}</td>
                </v-flex>
            </slot>
            <slot v-if="crammStagesAdded" name="footer-buttons">
            </slot>
        </v-layout>
            </v-form>
        </v-card>
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'
    import { mapGetters } from 'vuex'
    import printingRiskMixin from 'mixin/PrintingRiskMixin'
    import Risk from 'domain/Risk'

    export default {
        name: 'RiskTemplate',
        props: ['risk', 'readonly', 'validation'],
        mixins: [printingRiskMixin],
        data() {
            return {
                crammStagesAdded: false
            }
        },
        mounted () {
          this.crammStagesAdded = this.risk !== undefined && this.risk.cramms !== undefined && this.risk.cramms.length > 0
          this.$emit('validationForm', this.$refs.form)
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
        methods: {
            addCRAMMCriteria() {
                this.$set(this.risk.cramms, this.risk.cramms.length, {'asset': '', 'assetRate': '', 'threat': '', 'vulnerability': '', 'vulnerabilityRate': ''})
            },
            removeCRAMMCriteria(index) {
                this.risk.cramms.splice(index, 1);
            }
        }
    }
</script>

<style scoped>

</style>