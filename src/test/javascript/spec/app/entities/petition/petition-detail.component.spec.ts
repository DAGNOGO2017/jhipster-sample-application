/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PetitionDetailComponent } from 'app/entities/petition/petition-detail.component';
import { Petition } from 'app/shared/model/petition.model';

describe('Component Tests', () => {
    describe('Petition Management Detail Component', () => {
        let comp: PetitionDetailComponent;
        let fixture: ComponentFixture<PetitionDetailComponent>;
        const route = ({ data: of({ petition: new Petition(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PetitionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PetitionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PetitionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.petition).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
