import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPetition } from 'app/shared/model/petition.model';

@Component({
    selector: 'jhi-petition-detail',
    templateUrl: './petition-detail.component.html'
})
export class PetitionDetailComponent implements OnInit {
    petition: IPetition;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ petition }) => {
            this.petition = petition;
        });
    }

    previousState() {
        window.history.back();
    }
}
