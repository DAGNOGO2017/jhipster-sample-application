import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPetition } from 'app/shared/model/petition.model';
import { PetitionService } from './petition.service';
import { IPersonne } from 'app/shared/model/personne.model';
import { PersonneService } from 'app/entities/personne';

@Component({
    selector: 'jhi-petition-update',
    templateUrl: './petition-update.component.html'
})
export class PetitionUpdateComponent implements OnInit {
    petition: IPetition;
    isSaving: boolean;

    personnes: IPersonne[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected petitionService: PetitionService,
        protected personneService: PersonneService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ petition }) => {
            this.petition = petition;
        });
        this.personneService.query().subscribe(
            (res: HttpResponse<IPersonne[]>) => {
                this.personnes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.petition.id !== undefined) {
            this.subscribeToSaveResponse(this.petitionService.update(this.petition));
        } else {
            this.subscribeToSaveResponse(this.petitionService.create(this.petition));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPetition>>) {
        result.subscribe((res: HttpResponse<IPetition>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPersonneById(index: number, item: IPersonne) {
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
