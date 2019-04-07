<template>
    <v-container text-xs-center>
        <v-card class="my-2 py-2" max-width="100%">
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
    </v-container>
</template>

<script>
    import { mapGetters } from 'vuex'
    export default {
        name: 'Profile',
        props: ['userId'],
        computed: { 
            ...mapGetters(['getUserById']),
            userProfile() {
                return this.getUserById(this.userId)
            }
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