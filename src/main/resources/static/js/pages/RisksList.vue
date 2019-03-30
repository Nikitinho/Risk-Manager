<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout align-space-around justify-start column>
        <v-toolbar tabs>
            <v-text-field class="my-2"
                    append-icon="mic"
                    flat
                    label="Search"
                    v-model="search"
                    prepend-inner-icon="search"
                    solo-inverted>
            </v-text-field>
            <template v-if="areFiltersApplied" v-slot:extension>
                <v-tabs class="my-2"
                        centered
                        height = "100%"
                        color="transparent">
                    <v-tab height = "100%">
                        <v-select
                                v-model="category"
                                :items="riskCategoriesItems"
                                label="Category"
                                solo
                                outline
                                hide-selected
                                flat>
                        </v-select>
                    </v-tab>
                    <v-tab>
                        <v-select
                                v-model="status"
                                :items="riskStatusesItems"
                                label="Status"
                                solo
                                outline
                                hide-selected
                                flat>
                        </v-select>
                    </v-tab>
                </v-tabs>
            </template>
            <v-btn flat round @click="() => this.areFiltersApplied = !this.areFiltersApplied">
                <v-icon dark>filter_list</v-icon>
            </v-btn>
        </v-toolbar>
        <v-container>
            <risk-row v-for="risk in filteredRisks"
                      :key="risk.id" :risk="risk"/>
    </v-container>
    </v-layout>
</template>

<script>
    import { mapGetters } from 'vuex'
    import RiskRow from 'components/risks/RiskRow.vue'
    import RiskForm from 'components/risks/RiskForm.vue'
    export default {
        components: {
          RiskRow,
          RiskForm
        },
        data() {
            return {
                risk: null,
                search: '',
                status: null,
                category: null,
                areFiltersApplied: false
            }
        },
        computed: {
            ...mapGetters(['sortedRisks', 'riskStatuses', 'riskCategories']),
            riskStatusesItems () {
                let items = this.riskStatuses.slice()
                items.push({value: null, text: 'Choose risk'})
                return items
            },
            riskCategoriesItems () {
                let items = this.riskCategories.slice()
                items.push({value: null, text: 'Choose category'})
                return items
            },
            filteredRisks() {
                let risks = this.sortedRisks
                if (this.search) {
                    risks = risks.filter(risk =>
                        (String)(risk.text).includes(this.search) || (String)(risk.description).includes(this.search))
                }

                if (this.status) {
                    risks = risks.filter(risk => risk.status === this.status)
                }

                if (this.category) {
                    risks = risks.filter(risk => risk.category === this.category)
                }

                return risks
            }
        }

    }
</script>

<style>
    .rounded-v-select {
        border-radius:50px;
    }
</style>