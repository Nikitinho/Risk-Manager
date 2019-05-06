<template>
    <v-container fluid>
        <v-layout align-space-around justify-start wrap>
            <v-flex :xs6="!$vuetify.breakpoint.mdAndDown" class="px-1 py-1">
                <v-hover>
                <v-card
                        slot-scope="{ hover }"
                        tile
                        max-width="534"
                        :class="`elevation-${hover ? 12 : 2} mx-auto`"
                >
                    <v-layout>
                        <v-flex shrink>
                            <v-avatar
                                    class="profile"
                                    :size="$vuetify.breakpoint.smAndUp ? 164 : 128"
                                    color="grey"
                                    tile
                            >
                                <v-img class="img-circle elevation-6" :src="userProfile.userpic" >
                                </v-img>
                            </v-avatar>
                        </v-flex>
                        <v-flex>
                            <v-img
                                    height="100%"
                                    alt="Avatar"
                            >
                                <v-layout
                                        align-end
                                        fill-height
                                >
                                    <v-list-tile
                                            dark
                                            color="rgba(0, 0, 0, .4)"
                                    >
                                        <v-list-tile-content>
                                            <v-list-tile-title class="title">
                                                {{ userProfile.name || $t('profile.userInfo.emptyField') }}
                                            </v-list-tile-title>
                                            <v-list-tile-sub-title>
                                                {{ userProfile.email || $t('profile.userInfo.emptyField') }}
                                            </v-list-tile-sub-title>
                                        </v-list-tile-content>
                                    </v-list-tile>
                                </v-layout>
                            </v-img>
                        </v-flex>
                    </v-layout>
                </v-card>
                </v-hover>

            </v-flex>
            <v-flex :xs6="!$vuetify.breakpoint.mdAndDown" class="px-1 py-1">
                <v-hover>
        <v-card slot-scope="{ hover }"
                :class="`elevation-${hover ? 12 : 2} mb-1`">
            <v-card-title>
                <span class="headline font-weight-light">{{$t('project.top10Risks.title')}}</span>
                <v-spacer></v-spacer>
                <v-btn color="success"
                       @click="showTop">
                    {{ isTopShown
                    ? $t('project.top10Risks.hideBtn')
                    : $t('project.top10Risks.showBtn') }}
                </v-btn>
            </v-card-title>
            <slot v-if="isTopShown">
            <v-list v-if="getProfileRisks && getProfileRisks.length > 0">
            <template v-for="risk in getProfileRisks.slice(0, 10)"
                      :v-key="risk.id">
                <v-divider></v-divider>

                <v-list-tile avatar
                             @click="() => { showRisk(risk) }">
                    <v-list-tile-avatar>
                        <v-icon :color="getColor(risk.riskRate)">report</v-icon>
                    </v-list-tile-avatar>

                    <v-list-tile-content>
                        <v-list-tile-title v-html="risk.text"></v-list-tile-title>
                    </v-list-tile-content>

                    <v-spacer></v-spacer>

                    <div class="text-xs-center">
                        <v-chip>{{$t(`project.statuses.${risk.status.toLowerCase()}`)}}</v-chip>
                    </div>

                </v-list-tile>
            </template>
            </v-list>
            </slot>
        </v-card>
                </v-hover>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapGetters, mapActions } from 'vuex'
    import Risk from 'domain/Risk'
    export default {
        name: 'Profile',
        props: ['userId'],
        data() {
            return {
                isTopShown: true
            }
        },
        computed: { 
            ...mapGetters(['getUserById', 'getProfileRisks']),
            userProfile() {
                return this.getUserById(this.userId)
            }
        },
        methods: {
            ...mapActions(['fetchUserRisks']),
            getColor(riskRate) {
                return Risk.convertRiskRateToColor(riskRate)
            },
            showTop() {
                this.isTopShown = !this.isTopShown
            },
            getResponsible(risk) {
                if (risk.responsible) {
                    if (risk.responsible.length <= this.maxRiskUsersAmount) {
                        return risk.responsible
                    } else {
                        return risk.responsible.slice(0, this.maxRiskUsersAmount)
                    }
                }
            },
            getExtraRiskUsersAmount(risk) {
                return risk.responsible.length - this.getResponsible(risk).length
            },
            showRisk(risk) {
                this.$router.push({ name: 'RiskView', params: { projectId: risk.project.id, riskId: risk.id } })
            }
        },
        created() {
            this.fetchUserRisks(this.userId)
        }
    }
</script>

<style scoped>
    img {
        max-width: 100%;
        height: auto;
    }
    .img-circle {
        border-radius: 50%;
    }
</style>