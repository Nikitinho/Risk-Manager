<template>
    <v-card class="my-2">
        <v-form ref="form"
                lazy-validation>
            <v-layout align-space-around justify-start column>
                <v-card-title>
                    <span class="headline font-weight-light">Comments</span>
                    <v-spacer></v-spacer>
                    <v-btn color="success" @click="() => isCommentCreation = !isCommentCreation">
                        <v-icon dark>add</v-icon>
                    </v-btn>
                </v-card-title>
                <span v-if="isCommentCreation">
                <v-card-text>
                    <v-divider></v-divider>
                    <v-layout row wrap align-center>
                    <v-text-field placeholder="Type your comment"
                                  v-model="newComment.text">
                    </v-text-field>
                    <v-btn @click="createComment" icon>
                        <v-icon dark>send</v-icon>
                    </v-btn>
                        </v-layout>
                </v-card-text>
                </span>
                <v-list v-if="comments && comments.length > 0">
                    <template v-for="(comment, index) in comments" :v-key="comment.id">

                        <v-divider></v-divider>

                        <v-list-tile avatar
                                     @click="">
                            <v-list-tile-avatar v-if="comment.author">
                                <v-img class="elevation-6"
                                       :src="comment.author.userpic">
                                </v-img>
                            </v-list-tile-avatar>

                            <v-list-tile-content>
                                <v-list-tile-title v-html="`${comment.text}`"></v-list-tile-title>
                            </v-list-tile-content>

                            <v-list-tile-action>
                            <v-btn icon @click="() => deleteComment(comment)">
                                <v-icon>delete</v-icon>
                            </v-btn>
                            </v-list-tile-action>
                        </v-list-tile>
                    </template>
                </v-list>
            </v-layout>
        </v-form>
    </v-card>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        name: 'RiskComments',
        props: ['comments', 'riskId'],
        data () {
            return {
                isCommentCreation: false,
                newComment: {
                    text: ''
                }
            }
        },
        methods: {
            ...mapActions(['addRCommentAction', 'updateRCommentAction', 'removeRCommentAction']),
            async createComment() {
                if (!this.newComment) { return }
                let rcomment = {
                    text: this.newComment.text,
                    risk: {
                        id: this.riskId
                    }
                }
                await this.addRCommentAction(rcomment)
                this.newComment.text = ''
            },
            async deleteComment(rcomment) {
                await this.removeRCommentAction(rcomment)
            }
        }
    }
</script>

<style scoped>
</style>