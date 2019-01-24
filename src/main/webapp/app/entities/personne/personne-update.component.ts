import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPersonne } from 'app/shared/model/personne.model';
import { PersonneService } from './personne.service';
import { IActivite } from 'app/shared/model/activite.model';
import { ActiviteService } from 'app/entities/activite';
import { IPetition } from 'app/shared/model/petition.model';
import { PetitionService } from 'app/entities/petition';

@Component({
    selector: 'jhi-personne-update',
    templateUrl: './personne-update.component.html'
})
export class PersonneUpdateComponent implements OnInit {
    personne: IPersonne;
    isSaving: boolean;

    activites: IActivite[];

    petitions: IPetition[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected personneService: PersonneService,
        protected activiteService: ActiviteService,
        protected petitionService: PetitionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personne }) => {
            this.personne = personne;
        });
        this.activiteService.query().subscribe(
            (res: HttpResponse<IActivite[]>) => {
                this.activites = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.petitionService.query().subscribe(
            (res: HttpResponse<IPetition[]>) => {
                this.petitions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.personne.id !== undefined) {
            this.subscribeToSaveResponse(this.personneService.update(this.personne));
        } else {
            this.subscribeToSaveResponse(this.personneService.create(this.personne));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonne>>) {
        result.subscribe((res: HttpResponse<IPersonne>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackActiviteById(index: number, item: IActivite) {
        return item.id;
    }

    trackPetitionById(index: number, item: IPetition) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
