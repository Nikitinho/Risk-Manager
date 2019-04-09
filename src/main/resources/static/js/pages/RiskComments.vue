<template>
    <v-card class="my-2">
        <v-form ref="form"
                lazy-validation>
            <v-layout align-space-around justify-start column>
                <v-card-title>
                    <span class="headline font-weight-light">Comments</span>
                    <v-spacer></v-spacer>
                    <v-btn color="success" @click="">
                        <v-icon dark>add</v-icon>
                    </v-btn>
                </v-card-title>
                <v-list v-if="comments && comments.length > 0">
                    <template v-for="(comment, index) in comments" :v-key="comment.id">

                        <v-divider></v-divider>

                        <v-list-tile avatar
                                     @click="">
                            <v-list-tile-avatar v-if="comments.author">
                                <v-img class="elevation-6"
                                       :src="comments.author.userpic">
                                </v-img>
                            </v-list-tile-avatar>

                            <v-list-tile-content>
                                <v-list-tile-title v-html="`${comments.text}`"></v-list-tile-title>
                            </v-list-tile-content>
                        </v-list-tile>
                    </template>
                </v-list>
            </v-layout>
        </v-form>
    </v-card>
</template>

<script>
    export default {
        name: 'RiskComments',
        props: ['comments'],
        data () {
            return {

            }
        }
    }
</script>

<style scoped>
</style>