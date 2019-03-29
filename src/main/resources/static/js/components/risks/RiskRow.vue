<template>
    <v-card class="my-2">
        <v-card-title>
            <v-icon large left>
                star_border
            </v-icon>
            <span class="title font-weight-light">{{ risk.text }}</span>
        </v-card-title>
        <v-card-text class="headline font-weight-bold">
            Risk description
        </v-card-text>
        <v-card-actions>
            <v-list-tile class="grow">
                <v-list-tile-avatar color="grey darken-3">
                    <v-img class="elevation-6"
                           src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                    ></v-img>
                </v-list-tile-avatar>

                <v-list-tile-content>
                    <v-list-tile-title>User Pic</v-list-tile-title>
                </v-list-tile-content>

                <v-layout align-center justify-end>
                    <v-btn fab flat @click="edit">
                        <v-icon dark>edit</v-icon>
                    </v-btn>
                    <v-btn fab flat @click="del">
                        <v-icon dark>delete</v-icon>
                    </v-btn>
                </v-layout>
            </v-list-tile>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        props: ['risk', 'editRisk', 'deleteRisk', 'risks'],
        methods: {
            edit () {
                this.editRisk(this.risk)
            },
            del () {
                this.deleteRisk(this.risk)
            }
        }
    }
</script>

<style>

</style>