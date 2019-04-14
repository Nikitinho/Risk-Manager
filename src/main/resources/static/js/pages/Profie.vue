<template>
    <v-container text-xs-center>
        <v-card class="my-2 py-2" max-width="100%" flat color="transparent">
            <v-layout justify-space-around>
            <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
                <v-layout row justify-space-between>
                    <v-flex class="px-1" align-self-center>
                        <v-img class="img-circle" :src="userProfile.userpic"></v-img>
                    </v-flex>
                    <v-flex align-self-center>
                        <v-layout class="px-1" column>
                            <v-divider></v-divider>
                            <v-flex>Name: {{ userProfile.name || 'Name is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Locale: {{ userProfile.locale || 'Locale is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Gender: {{ userProfile.gender || 'Gender is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Email: {{ userProfile.email || 'Email is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Last action: {{ userProfile.lastVisit || 'Last action is not provided' }}</v-flex>
                            <v-divider></v-divider>
                        </v-layout>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
        </v-card>
        <v-card class="my-2 py-2" max-width="100%" flat color="transparent">
            <v-list v-if="getProfileRisks && getProfileRisks.length > 0">
            <template v-for="risk in getProfileRisks"
                      :v-key="risk.id">
                <v-divider></v-divider>

                <v-list-tile avatar
                             @click="">
                    <!--<v-list-tile-avatar>-->
                        <!--<v-icon :color="getColor(risk.riskRate)">report</v-icon>-->
                    <!--</v-list-tile-avatar>-->

                    <v-list-tile-content>
                        <v-list-tile-title v-html="risk.text"></v-list-tile-title>
                    </v-list-tile-content>

                    <v-spacer></v-spacer>

                    <!--<div class="text-xs-center">-->
                        <!--<v-chip>{{getRiskStatus(risk)}}</v-chip>-->
                    <!--</div>-->

                    <!--<v-divider vertical></v-divider>-->

                    <!--<v-list-tile-avatar v-if="!risk.responsible || risk.responsible.length === 0">-->
                        <!--<span class="cetered-text-span">?</span>-->
                    <!--</v-list-tile-avatar>-->

                    <!--<v-list-tile-avatar v-else v-for="user in getResponsible(risk)">-->
                        <!--<v-img class="elevation-6"-->
                               <!--:src="user.userpic">-->
                        <!--</v-img>-->
                    <!--</v-list-tile-avatar>-->

                    <!--<v-list-tile-avatar v-if="getExtraRiskUsersAmount(risk) > 0">-->
                        <!--<span class="cetered-text-span">+{{getExtraRiskUsersAmount(risk)}}</span>-->
                    <!--</v-list-tile-avatar>-->

                </v-list-tile>
            </template>
            </v-list>
        </v-card>
    </v-container>
</template>

<script>
    import { mapGetters, mapActions } from 'vuex'
    export default {
        name: 'Profile',
        props: ['userId'],
        computed: { 
            ...mapGetters(['getUserById', 'getProfileRisks']),
            userProfile() {
                return this.getUserById(this.userId)
            }
        },
        methods: {
            ...mapActions(['fetchUserRisks'])
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