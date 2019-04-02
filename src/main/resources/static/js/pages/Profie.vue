<template>
    <v-container text-xs-center>
        <v-card class="my-2 py-2" max-width="100%">
        <v-layout justify-space-around>
            <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
                <v-layout row justify-space-between>
                    <v-flex class="px-1" align-self-center>
                        <v-img class="img-circle" :src="profile.userpic"></v-img>
                    </v-flex>
                    <v-flex align-self-center>
                        <v-layout class="px-1" column>
                            <v-divider></v-divider>
                            <v-flex>Name: {{ profile.name || 'Name is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Locale: {{ profile.locale || 'Locale is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Gender: {{ profile.gender || 'Gender is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Email: {{ profile.email || 'Email is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Last action: {{ profile.lastVisit || 'Last action is not provided' }}</v-flex>
                            <v-divider></v-divider>
                        </v-layout>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
        </v-card>
    </v-container>
</template>

<script>
    import { mapState } from 'vuex'
    export default {
        name: 'Profile',
        computed: mapState(['profile'])
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