import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonne } from 'app/shared/model/personne.model';

@Component({
    selector: 'jhi-personne-detail',
    templateUrl: './personne-detail.component.html'
})
export class PersonneDetailComponent implements OnInit {
    personne: IPersonne;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personne }) => {
            this.personne = personne;
        });
    }

    previousState() {
        window.history.back();
    }
}
